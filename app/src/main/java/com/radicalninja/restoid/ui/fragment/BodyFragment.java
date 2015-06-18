package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.radicalninja.restoid.R;

public class BodyFragment extends Fragment {

    private static final String BUNDLE_KEY_BODY_INPUT = "BodyInput";

    private EditText mBodyInput;

    public static BodyFragment newInstance() {
        // TODO: In the future, saved connections will be used here for building new fragments.
        return new BodyFragment();
    }

    public BodyFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_body, container, false);
        mBodyInput = (EditText) rootView.findViewById(R.id.text_body_input);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mBodyInput != null && savedInstanceState != null) {
            mBodyInput.setText(savedInstanceState.getString(BUNDLE_KEY_BODY_INPUT));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_KEY_BODY_INPUT, mBodyInput.getText().toString());
    }
}
