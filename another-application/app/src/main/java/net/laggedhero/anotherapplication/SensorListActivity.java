package net.laggedhero.anotherapplication;

import android.app.ListActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class SensorListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        List<String> sensorNameList = new ArrayList<>(sensorList.size());
        for (Sensor sensor : sensorList) {
            sensorNameList.add(sensor.getName());
        }

        setListAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                sensorNameList
        ));
    }
}
