package net.laggedhero.anotherapplication;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MapClusterItem implements ClusterItem {

    private LatLng position;

    public MapClusterItem(double lat, double lng) {
        position = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }
}
