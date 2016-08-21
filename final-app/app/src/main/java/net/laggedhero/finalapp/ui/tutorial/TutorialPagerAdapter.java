package net.laggedhero.finalapp.ui.tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TutorialPagerAdapter extends FragmentPagerAdapter {

    public TutorialPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TutorialStepOneFragment();
            case 1:
                return new TutorialStepTwoFragment();
            default:
                return new TutorialStepThreeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
