package net.laggedhero.finalapp.ui.main;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

import net.laggedhero.finalapp.R;
import net.laggedhero.finalapp.services.NasaApodService;
import net.laggedhero.finalapp.ui.base.BaseActivity;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

public class MainActivity extends BaseActivity {

    @Inject
    FirebaseDatabase firebaseDatabase;

    @Inject
    NasaApodService nasaApodService;

    @Inject
    EntityDataStore<Persistable> dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFinalAppApplication().getApplicationComponent().inject(this);

        setContentView(R.layout.activity_main);
    }
}
