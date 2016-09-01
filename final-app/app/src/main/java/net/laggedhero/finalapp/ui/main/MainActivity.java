package net.laggedhero.finalapp.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.laggedhero.finalapp.R;
import net.laggedhero.finalapp.models.NasaApod;
import net.laggedhero.finalapp.services.NasaApodService;
import net.laggedhero.finalapp.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.requery.sql.EntityDataStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private static final SimpleDateFormat APOD_DATE = new SimpleDateFormat("yyyy-MM-dd");

    @Inject
    FirebaseDatabase firebaseDatabase;

    @Inject
    NasaApodService nasaApodService;

    @Inject
    EntityDataStore<Object> dataStore;

    @BindView(R.id.picture)
    ImageView picture;

    @BindView(R.id.infoArea)
    View infoArea;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.copyright)
    TextView copyright;

    @BindView(R.id.explanation)
    TextView explanation;

    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APOD_DATE.setTimeZone(TimeZone.getTimeZone("UTC"));

        getFinalAppApplication().getApplicationComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        infoArea.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

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
    }

    private void loadNasaApod(String apiKey) {
        // from cache first
        final NasaApod nasaApod = getCachedResponse();
        if (nasaApod != null) {
            populateFrom(nasaApod);
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
                populateFrom(response.body());
            }

            @Override
            public void onFailure(Call<NasaApod> call, Throwable t) {
                // error - see it later
            }
        });
    }

    private void cacheResponse(NasaApod nasaApod) {
        dataStore.insert(nasaApod);
    }

    private NasaApod getCachedResponse() {
        return dataStore.findByKey(NasaApod.class, getTodayApodDate());
    }

    private void populateFrom(NasaApod nasaApod) {
        title.setText(nasaApod.getTitle());

        if (TextUtils.isEmpty(nasaApod.getCopyright())) {
            copyright.setVisibility(View.GONE);
        } else {
            copyright.setVisibility(View.VISIBLE);
            copyright.setText(nasaApod.getCopyright());
        }

        explanation.setText(nasaApod.getExplanation());

        Glide.with(this)
                .load(nasaApod.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // error
                        progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .centerCrop()
                .into(picture);

        infoArea.setVisibility(View.VISIBLE);
    }

    private String getTodayApodDate() {
        return APOD_DATE.format(new Date());
    }
}
