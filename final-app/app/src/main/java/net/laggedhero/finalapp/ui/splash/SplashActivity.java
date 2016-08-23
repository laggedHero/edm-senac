package net.laggedhero.finalapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import net.laggedhero.finalapp.R;
import net.laggedhero.finalapp.ui.base.BaseActivity;
import net.laggedhero.finalapp.ui.main.MainActivity;
import net.laggedhero.finalapp.ui.tutorial.TutorialActivity;
import net.laggedhero.finalapp.utils.PreferenceUtils;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_TIMEOUT = 2000;

    @Inject
    PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFinalAppApplication().getApplicationComponent().inject(this);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (preferenceUtils.isTutorialDone()) {
                startMain();
            } else {
                startTutorial();
            }
        }, SPLASH_TIMEOUT);
    }

    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void startTutorial() {
        startActivity(new Intent(this, TutorialActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
