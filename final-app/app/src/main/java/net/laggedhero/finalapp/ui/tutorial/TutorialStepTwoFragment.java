package net.laggedhero.finalapp.ui.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.laggedhero.finalapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialStepTwoFragment extends Fragment {

    public TutorialStepTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial_step_two, container, false);
    }

}
