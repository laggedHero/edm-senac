package net.laggedhero.anotherapplication;

import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.GridBasedAlgorithm;
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;

    private ClusterManager<MapClusterItem> clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        setClusterManager();

        initGoogleClient();
    }

    private void setClusterManager() {
        clusterManager = new ClusterManager<>(this, googleMap);

        googleMap.setOnCameraChangeListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        showMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //
    }

    private void initGoogleClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void showMyLocation() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            googleMap.addMarker(new MarkerOptions().position(latLng).title("My marker"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            addItems();
        }
    }

    private void addItems() {
        Call<Positions> positionsCall = ((AnotherApplication) getApplication()).service.searchPositions();

        positionsCall.enqueue(new Callback<Positions>() {
            @Override
            public void onResponse(Call<Positions> call, Response<Positions> response) {
                Positions positions = response.body();
                for (int i = 0, size = positions.posicoes.size(); i < size; i++) {
                    final Position position = positions.posicoes.get(i);
                    clusterManager.addItem(new MapClusterItem(position.latitude, position.longitude));
                }
            }

            @Override
            public void onFailure(Call<Positions> call, Throwable t) {
                //
            }
        });
    }
}
