package net.laggedhero.finalapp.ui.tutorial;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.Button;

import net.laggedhero.finalapp.R;
import net.laggedhero.finalapp.ui.base.BaseActivity;
import net.laggedhero.finalapp.ui.main.MainActivity;
import net.laggedhero.finalapp.utils.PreferenceUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialActivity extends BaseActivity {

    @Inject
    PreferenceUtils preferenceUtils;

    @BindView(R.id.container)
    ViewPager viewPager;

    @BindView(R.id.skipButton)
    Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFinalAppApplication().getApplicationComponent().inject(this);

        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        final TutorialPagerAdapter tutorialPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tutorialPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position < 2) {
                    skipButton.setText(R.string.tutorial_skip_action);
                } else {
                    skipButton.setText(R.string.tutorial_ok_action);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        skipButton.setOnClickListener(view -> {
            preferenceUtils.markTutorialAsDone();
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }
}
