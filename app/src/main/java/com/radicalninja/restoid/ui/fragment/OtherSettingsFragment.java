package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radicalninja.restoid.R;

public class OtherSettingsFragment extends Fragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OtherSettingsFragment newInstance() {
        // TODO: In the future, saved connections will be used here for building new fragments.
        return new OtherSettingsFragment();
    }

    public OtherSettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other_settings, container, false);
        return rootView;
    }
}
