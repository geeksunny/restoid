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

public class RequestFragment extends BaseConnectionFragment {

    public enum RequestType {
        GET, POST, PATCH, DELETE
    }

    public enum ResultType {
        FORMATTED, JSON, RAW
    }

    private EditText mUrl;
    private RadioGroup mRequestTypes, mResultsTypes;
    private TextView mResults;
    private TextWatcher mTextWatcher;

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
        mUrl = (EditText) rootView.findViewById(R.id.text_url);
        mRequestTypes = (RadioGroup) rootView.findViewById(R.id.request_types);
        mResultsTypes = (RadioGroup) rootView.findViewById(R.id.results_types);
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

    @Override
    protected void populateConnectionInfo(final Connection connection) {
        // Set the URL, and then setup the textwatcher
        if (mTextWatcher != null) {
            mUrl.removeTextChangedListener(mTextWatcher);
        }
        mUrl.setText(connection.getUrl());
        mTextWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                connection.setUrl(s.toString());
                onConnectionChanged();
            }
        };
        mUrl.addTextChangedListener(mTextWatcher);
        // Checking the appropriate radio buttons...
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
        // ... And then setting up their onCheckChangedListeners.
        mRequestTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onConnectionChanged();
                switch (checkedId) {
                    case R.id.request_post:
                        connection.setRequestType(RequestType.POST);
                        break;
                    case R.id.request_patch:
                        connection.setRequestType(RequestType.PATCH);
                        break;
                    case R.id.request_delete:
                        connection.setRequestType(RequestType.DELETE);
                        break;
                    case R.id.request_get:
                    default:
                        connection.setRequestType(RequestType.GET);
                }
            }
        });
        mResultsTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onConnectionChanged();
                switch (checkedId) {
                    case R.id.results_formatted:
                        connection.setResultType(ResultType.FORMATTED);
                        break;
                    case R.id.results_json:
                        connection.setResultType(ResultType.JSON);
                        break;
                    case R.id.results_raw:
                    default:
                        connection.setResultType(ResultType.RAW);
                }
            }
        });

    }

}
