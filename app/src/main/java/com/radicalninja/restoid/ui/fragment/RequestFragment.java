package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ApiResponseEvent;
import com.radicalninja.restoid.data.event.ConnectionDataEvent;
import com.radicalninja.restoid.data.model.Connection;
import com.radicalninja.restoid.data.model.UrlEntry;
import com.radicalninja.restoid.util.Ln;
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
        mUrl.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (getConnection() != null) {
                    getConnection().setmUrl(s.toString());
                }
            }
        });
        mRequestTypes = (RadioGroup) rootView.findViewById(R.id.request_types);
        mRequestTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Connection connection = getConnection();
                if (getConnection() == null) {
                    return;
                }
                switch (checkedId) {
                    case R.id.request_post:
                        connection.setRequestType(RequestType.POST);
                    case R.id.request_patch:
                        connection.setRequestType(RequestType.PATCH);
                    case R.id.request_delete:
                        connection.setRequestType(RequestType.DELETE);
                    case R.id.request_get:
                    default:
                        connection.setRequestType(RequestType.GET);
                }
            }
        });
        mResultsTypes = (RadioGroup) rootView.findViewById(R.id.results_types);
        mResultsTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Connection connection = getConnection();
                if (getConnection() == null) {
                    return;
                }
                switch (checkedId) {
                    case R.id.results_formatted:
                        connection.setResultType(ResultType.FORMATTED);
                    case R.id.results_json:
                        connection.setResultType(ResultType.JSON);
                    case R.id.results_raw:
                    default:
                        connection.setResultType(ResultType.RAW);
                }
            }
        });
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
        Ln.i("ApiResponseEvent received.");
        mResults.setText(event.response);
    }

    @Override
    protected void populateConnectionInfo(Connection connection) {
        Ln.i("Populating Connection Info on RequestFragment");
        mUrl.setText(connection.getUrl());
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
