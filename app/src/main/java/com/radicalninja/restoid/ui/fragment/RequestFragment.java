package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ApiResponseEvent;
import com.radicalninja.restoid.data.model.UrlEntry;
import com.squareup.otto.Subscribe;

public class RequestFragment extends Fragment {

    private static final String BUNDLE_KEY_URL = "URL";
    private static final String BUNDLE_KEY_REQUEST_TYPE = "RequestType";
    private static final String BUNDLE_KEY_RESULTS_TYPE = "ResultsType";
    private static final String BUNDLE_KEY_RESULTS = "Results";

    // TODO: Parse mUrl for the base URL and the URL's path. Base URL gets sent to the DynamicEndpoint and path goes into the POST method.
    private EditText mUrl;
    private RadioGroup mRequestTypes, mResultsTypes;
    private TextView mResults;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RequestFragment newInstance() {
        // TODO: In the future, saved connections will be used here for building new fragments.
        return new RequestFragment();
    }

    public RequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request, container, false);
        mUrl = (EditText) rootView.findViewById(R.id.text_url);
        mRequestTypes = (RadioGroup) rootView.findViewById(R.id.request_types);
        mResultsTypes = (RadioGroup) rootView.findViewById(R.id.results_types);
        mResults = (TextView) rootView.findViewById(R.id.text_results);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mUrl.setText(savedInstanceState.getString(BUNDLE_KEY_URL, ""));
            mRequestTypes.check(savedInstanceState.getInt(BUNDLE_KEY_REQUEST_TYPE));
            mResultsTypes.check(savedInstanceState.getInt(BUNDLE_KEY_RESULTS_TYPE));
            mResults.setText(savedInstanceState.getString(BUNDLE_KEY_RESULTS, ""));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_KEY_URL, mUrl.getText().toString());
        outState.putInt(BUNDLE_KEY_REQUEST_TYPE, mRequestTypes.getCheckedRadioButtonId());
        outState.putInt(BUNDLE_KEY_RESULTS_TYPE, mResultsTypes.getCheckedRadioButtonId());
        outState.putString(BUNDLE_KEY_RESULTS, mResults.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        App.getOttoBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        App.getOttoBus().unregister(this);
    }

    @Subscribe
    public void responseReceived(ApiResponseEvent event) {
        mResults.setText(event.response);
    }

    public UrlEntry getUrlEntry() {
        return new UrlEntry(mUrl.getText().toString());
    }
}
