package net.laggedhero.finalapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.laggedhero.finalapp.models.NasaApod;
import net.laggedhero.finalapp.services.NasaApodService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;

import io.requery.sql.EntityDataStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApodService extends Service {

    public static final String CACHE_REFRESHED = "CACHE_REFRESHED";

    private static final SimpleDateFormat APOD_DATE = new SimpleDateFormat("yyyy-MM-dd");

    @Inject
    FirebaseDatabase firebaseDatabase;

    @Inject
    NasaApodService nasaApodService;

    @Inject
    EntityDataStore<Object> dataStore;

    @Override
    public void onCreate() {
        super.onCreate();

        APOD_DATE.setTimeZone(TimeZone.getTimeZone("UTC"));

        ((FinalAppApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        firebaseDatabase.getReference().child("nasa-api-key").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadNasaApod(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadNasaApod("DEMO_KEY");
            }
        });

        return START_STICKY;
    }

    private void loadNasaApod(String apiKey) {
        // from cache first
        final NasaApod nasaApod = getCachedResponse();
        if (nasaApod != null) {
            broadcast();
            return;
        }

        requestNasaApod(apiKey);
    }

    private void requestNasaApod(String apiKey) {
        Call<NasaApod> nasaApodCall = nasaApodService.getTodayPhoto(apiKey);

        nasaApodCall.enqueue(new Callback<NasaApod>() {
            @Override
            public void onResponse(Call<NasaApod> call, Response<NasaApod> response) {
                // wow. much success
                cacheResponse(response.body());
                broadcast();
            }

            @Override
            public void onFailure(Call<NasaApod> call, Throwable t) {
                // error - see it later
                broadcast();
            }
        });
    }

    private void broadcast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(CACHE_REFRESHED));
        stopSelf();
    }

    private void cacheResponse(NasaApod nasaApod) {
        dataStore.insert(nasaApod);
    }

    private NasaApod getCachedResponse() {
        return dataStore.findByKey(NasaApod.class, getTodayApodDate());
    }

    private String getTodayApodDate() {
        return APOD_DATE.format(new Date());
    }
}
