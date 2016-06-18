package net.laggedhero.anotherapplication.navigation;

import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import net.laggedhero.anotherapplication.AnotherApplication;
import net.laggedhero.anotherapplication.R;
import net.laggedhero.anotherapplication.navigation.data.Bounds;
import net.laggedhero.anotherapplication.navigation.data.Directions;
import net.laggedhero.anotherapplication.navigation.data.Leg;
import net.laggedhero.anotherapplication.navigation.data.Route;
import net.laggedhero.anotherapplication.navigation.data.Step;
import net.laggedhero.anotherapplication.utils.DisplayUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private CardView navigationCardView;
    private EditText originEditText;
    private EditText destinationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        navigationCardView = (CardView) findViewById(R.id.navigationCard);

        originEditText = (EditText) findViewById(R.id.origin);

        destinationEditText = (EditText) findViewById(R.id.destination);
        if (destinationEditText != null) {
            destinationEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        drawNavigation();
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void drawNavigation() {
        if (googleMap == null) {
            // not ready
            Toast.makeText(this, "Chola mais!", Toast.LENGTH_LONG).show();
            return;
        }

        String origin = originEditText.getText().toString();
        String destination = destinationEditText.getText().toString();
        if (TextUtils.isEmpty(origin)
                || TextUtils.isEmpty(destination)) {
            Toast.makeText(this, "Chola mais! E preenche!", Toast.LENGTH_LONG).show();
            return;
        }

        Call<Directions> directionsCall = ((AnotherApplication) getApplication()).googleNavigationService.getDirections(
                origin.trim(), destination.trim(), getString(R.string.google_server_key)
        );

        directionsCall.enqueue(new Callback<Directions>() {
            @Override
            public void onResponse(Call<Directions> call, Response<Directions> response) {
                plotDirections(response.body());
            }

            @Override
            public void onFailure(Call<Directions> call, Throwable t) {
                //
            }
        });
    }

    private void plotDirections(Directions directions) {
        googleMap.clear();

        if (!directions.status.equalsIgnoreCase("OK")) {
            Toast.makeText(this, "Chola mais! E sei lá!", Toast.LENGTH_LONG).show();
            return;
        }

        if (directions.routes.size() == 0) {
            Toast.makeText(this, "Chola mais! E não tem routes!", Toast.LENGTH_LONG).show();
            return;
        }

        final Route route = directions.routes.get(0);
        if (route.legs.size() == 0) {
            Toast.makeText(this, "Chola mais! E não tem legs!", Toast.LENGTH_LONG).show();
            return;
        }

        if (route.legs.get(0).steps.size() == 0) {
            Toast.makeText(this, "Chola mais! E não tem steps!", Toast.LENGTH_LONG).show();
            return;
        }

        Bounds bounds = route.bounds;
        Leg leg = route.legs.get(0);
        List<Step> steps = leg.steps;

        PolylineOptions polylineOptions = new PolylineOptions();

//        for (int i = 0, size = steps.size(); i < size; i++) {
//            final Step step = steps.get(i);
//            polylineOptions.add(step.start_location.getLatLng());
//        }

//        polylineOptions.add(steps.get(steps.size() - 1).end_location.getLatLng());

        googleMap.addMarker(new MarkerOptions().position(steps.get(0).start_location.getLatLng()).title(leg.start_address));
        googleMap.addMarker(new MarkerOptions().position(steps.get(steps.size() - 1).end_location.getLatLng()).title(leg.end_address));

        List<LatLng> latLngList = PolyUtil.decode(route.overview_polyline.points);
        polylineOptions.addAll(latLngList);

        googleMap.addPolyline(polylineOptions);

        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(bounds.northeast.getLatLng())
                .include(bounds.southwest.getLatLng())
                .build();

        int screenWidth = DisplayUtils.screenWidth(this);
        int screenHeight = DisplayUtils.screenHeight(this) - (int) DisplayUtils.dipToPixels(this, 48)
                - navigationCardView.getHeight();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                latLngBounds, screenWidth, screenHeight, (int) DisplayUtils.dipToPixels(this, 32)
        ));

        googleMap.moveCamera(CameraUpdateFactory.scrollBy(
                0, -1 * navigationCardView.getHeight() / 2
        ));
    }
}
