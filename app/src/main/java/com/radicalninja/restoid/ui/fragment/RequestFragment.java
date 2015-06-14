package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ApiResponseEvent;
import com.squareup.otto.Subscribe;

public class RequestFragment extends Fragment {

    // TODO: Parse mUrl for the base URL and the URL's path. Base URL gets sent to the DynamicEndpoint and path goes into the POST method.
    private TextView mUrl;
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
        mUrl = (TextView) rootView.findViewById(R.id.text_url);
        mResults = (TextView) rootView.findViewById(R.id.text_results);
        return rootView;
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
