package net.laggedhero.anotherapplication.navigation.data;

import com.google.android.gms.maps.model.LatLng;

public class Location {
    public double lat;
    public double lng;

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}
