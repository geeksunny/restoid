package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ApiResponseEvent;
import com.radicalninja.restoid.data.model.Connection;
import com.squareup.otto.Subscribe;

import java.util.Map;

public class RequestFragment extends BaseConnectionFragment {

    public enum RequestType {
        GET, POST, PATCH, DELETE
    }

    public enum ResultType {
        FORMATTED, JSON, RAW
    }

    private EditText mName, mUrl;
    private RadioGroup mRequestTypes, mResultsTypes;
    private TextView mLabelHeaders, mLabelBody, mResultsHeaders, mResultsBody;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RequestFragment newInstance() {
        return new RequestFragment();
    }

    public RequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request, container, false);
        mName = (EditText) rootView.findViewById(R.id.text_name);
        mUrl = (EditText) rootView.findViewById(R.id.text_url);
        mRequestTypes = (RadioGroup) rootView.findViewById(R.id.request_types);
        mResultsTypes = (RadioGroup) rootView.findViewById(R.id.results_types);

        mLabelHeaders = (TextView) rootView.findViewById(R.id.text_header_headers);
        mResultsHeaders = (TextView) rootView.findViewById(R.id.text_headers);
        mLabelBody = (TextView) rootView.findViewById(R.id.text_header_body);
        mResultsBody = (TextView) rootView.findViewById(R.id.text_results);

        mName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (getConnection() != null) {
                    getConnection().setName(s.toString());
                    onConnectionChanged();
                }
            }
        });
        mUrl.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (getConnection() != null) {
                    getConnection().setUrl(s.toString());
                    onConnectionChanged();
                }
            }
        });

        mRequestTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (getConnection() == null) {
                    return;
                }
                onConnectionChanged();
                switch (checkedId) {
                    case R.id.request_post:
                        getConnection().setRequestType(RequestType.POST);
                        break;
                    case R.id.request_patch:
                        getConnection().setRequestType(RequestType.PATCH);
                        break;
                    case R.id.request_delete:
                        getConnection().setRequestType(RequestType.DELETE);
                        break;
                    case R.id.request_get:
                    default:
                        getConnection().setRequestType(RequestType.GET);
                }
            }
        });
        mResultsTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (getConnection() == null) {
                    return;
                }
                onConnectionChanged();
                switch (checkedId) {
                    case R.id.results_formatted:
                        getConnection().setResultType(ResultType.FORMATTED);
                        break;
                    case R.id.results_json:
                        getConnection().setResultType(ResultType.JSON);
                        break;
                    case R.id.results_raw:
                    default:
                        getConnection().setResultType(ResultType.RAW);
                }
            }
        });

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
        if (event.success) {
            toggleLabels(true);
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> header : event.headers.entrySet()) {
                sb.append(String.format("%s : %s\n", header.getKey(), header.getValue()));
            }
            mResultsHeaders.setText(sb.toString());
            mResultsBody.setText(event.body);
        } else {
            toggleLabels(false);
            mResultsBody.setText(event.body);
        }
    }

    private void toggleLabels(boolean isVisible) {
        int visibility = (isVisible) ? View.VISIBLE : View.GONE;
        mLabelHeaders.setVisibility(visibility);
        mResultsHeaders.setVisibility(visibility);
        mLabelBody.setVisibility(visibility);
    }

    @Override
    protected void populateConnectionInfo(final Connection connection) {
        // Set the name and URL
        mName.setText(connection.getName());
        mUrl.setText(connection.getUrl());
        // Checking the appropriate radio buttons
        int id = 0;
        switch (connection.getRequestType()) {
            case POST:
                id = R.id.request_post;
                break;
            case PATCH:
                id = R.id.request_patch;
                break;
            case DELETE:
                id = R.id.request_delete;
                break;
            case GET:
            default:
                id = R.id.request_get;
        }
        mRequestTypes.check(id);
        switch (connection.getResultType()) {
            case FORMATTED:
                id = R.id.results_formatted;
                break;
            case JSON:
                id = R.id.results_json;
                break;
            case RAW:
            default:
                id = R.id.results_raw;
        }
        mResultsTypes.check(id);

    }

}
