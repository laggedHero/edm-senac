package net.laggedhero.finalapp.ui.base;

import android.support.v7.app.AppCompatActivity;

import net.laggedhero.finalapp.FinalAppApplication;

public abstract class BaseActivity extends AppCompatActivity {

    protected FinalAppApplication getFinalAppApplication() {
        return (FinalAppApplication) getApplication();
    }
}
