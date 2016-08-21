package net.laggedhero.finalapp.utils;

import android.content.SharedPreferences;

public class PreferenceUtils {

    private final static String TUTORIAL_DONE = "TUTORIAL_DONE";

    private SharedPreferences sharedPreferences;

    public PreferenceUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean isTutorialDone() {
        return getBoolean(TUTORIAL_DONE);
    }

    public void markTutorialAsDone() {
        setBoolean(TUTORIAL_DONE, true);
    }

    private boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    private void setBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
}
