package com.radicalninja.restoid.data.rest.api;

import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ApiResponseEvent;
import com.radicalninja.restoid.data.model.Connection;
import com.radicalninja.restoid.data.model.HeaderEntry;
import com.radicalninja.restoid.data.model.UrlEntry;
import com.radicalninja.restoid.data.rest.adapter.RestAdapter;
import com.radicalninja.restoid.data.rest.client.RestClient;
import com.radicalninja.restoid.data.rest.interceptor.RestInterceptor;
import com.radicalninja.restoid.util.ResponseUtils;

import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Api {

    private RestClient client = RestAdapter.getInstance().getRestClient();

    private Callback<Object> mCallback = new Callback<Object>() {
        @Override
        public void success(Object o, Response response) {
            App.getOttoBus().post(new ApiResponseEvent(ResponseUtils.getResponseText(response)));
        }

        @Override
        public void failure(RetrofitError error) {
            App.getOttoBus().post(new ApiResponseEvent(error.toString()));
        }
    };

    public void sendRequest(Connection connection) {
        UrlEntry entry = new UrlEntry(connection.getUrl());
        App.getEndpoint().setUrl(entry.getUrlBase());
        // Send the enabled headers into the Interceptor.
        RestInterceptor interceptor = RestInterceptor.getInstance();
        interceptor.removeAllHeaders();
        for (HeaderEntry header : connection.getHeaders()) {
            interceptor.addHeader(header.getKey(), header.getValue());
        }
        // Routing the request based on type
        switch (connection.getRequestType()) {
            case GET:
                routeGET(connection);
                break;
            case POST:
                routePOST(connection);
                break;
            case PATCH:
                routePATCH(connection);
                break;
            case DELETE:
                routeDELETE(connection);
                break;
        }
    }

    private void routeGET(Connection connection) {
        if (connection.hasQuery() && !connection.getBodyText().isEmpty()) {
            submitGET(connection.getUrl(), connection.getQuery().getQueryMap(), connection.getBodyText());
        } else if (connection.hasQuery()) {
            submitGET(connection.getUrl(), connection.getQuery().getQueryMap());
        } else if (!connection.getBodyText().isEmpty()) {
            submitGET(connection.getUrl(), connection.getBodyText());
        } else {
            submitGET(connection.getUrl());
        }
    }

    private void routePOST(Connection connection) {
        if (connection.hasQuery() && !connection.getBodyText().isEmpty()) {
            submitPOST(connection.getUrl(), connection.getQuery().getQueryMap(), connection.getBodyText());
        } else if (connection.hasQuery()) {
            submitPOST(connection.getUrl(), connection.getQuery().getQueryMap());
        } else if (!connection.getBodyText().isEmpty()) {
            submitPOST(connection.getUrl(), connection.getBodyText());
        } else {
            submitPOST(connection.getUrl());
        }
    }

    private void routePATCH(Connection connection) {
        if (connection.hasQuery() && !connection.getBodyText().isEmpty()) {
            submitPATCH(connection.getUrl(), connection.getQuery().getQueryMap(), connection.getBodyText());
        } else if (connection.hasQuery()) {
            submitPATCH(connection.getUrl(), connection.getQuery().getQueryMap());
        } else if (!connection.getBodyText().isEmpty()) {
            submitPATCH(connection.getUrl(), connection.getBodyText());
        } else {
            submitPATCH(connection.getUrl());
        }
    }

    private void routeDELETE(Connection connection) {
        if (connection.hasQuery() && !connection.getBodyText().isEmpty()) {
            submitDELETE(connection.getUrl(), connection.getQuery().getQueryMap(), connection.getBodyText());
        } else if (connection.hasQuery()) {
            submitDELETE(connection.getUrl(), connection.getQuery().getQueryMap());
        } else if (!connection.getBodyText().isEmpty()) {
            submitDELETE(connection.getUrl(), connection.getBodyText());
        } else {
            submitDELETE(connection.getUrl());
        }
    }

    public void submitGET(String url) {
        client.getEndpoint(url, mCallback);
    }

    public void submitGET(String url, Map<String, String> query) {
        client.getEndpoint(url, query, mCallback);
    }

    public void submitGET(String url, String body) {
        client.getEndpoint(url, body, mCallback);
    }

    public void submitGET(String url, Map<String, String> query, String body) {
        client.getEndpoint(url, query, body, mCallback);
    }

    public void submitPOST(String url) {
        client.postEndpoint(url, mCallback);
    }

    public void submitPOST(String url, Map<String, String> query) {
        client.postEndpoint(url, query, mCallback);
    }

    public void submitPOST(String url, String body) {
        client.postEndpoint(url, body, mCallback);
    }

    public void submitPOST(String url, Map<String, String> query, String body) {
        client.postEndpoint(url, query, body, mCallback);
    }

    public void submitPATCH(String url) {
        client.patchEndpoint(url, mCallback);
    }

    public void submitPATCH(String url, Map<String, String> query) {
        client.patchEndpoint(url, query, mCallback);
    }

    public void submitPATCH(String url, String body) {
        client.patchEndpoint(url, body, mCallback);
    }

    public void submitPATCH(String url, Map<String, String> query, String body) {
        client.patchEndpoint(url, query, body, mCallback);
    }

    public void submitDELETE(String url) {
        client.deleteEndpoint(url, mCallback);
    }

    public void submitDELETE(String url, Map<String, String> query) {
        client.deleteEndpoint(url, query, mCallback);
    }

    public void submitDELETE(String url, String body) {
        client.deleteEndpoint(url, body, mCallback);
    }

    public void submitDELETE(String url, Map<String, String> query, String body) {
        client.deleteEndpoint(url, query, body, mCallback);
    }

}
