package com.radicalninja.restoid.data.rest.api;

import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ApiResponseEvent;
import com.radicalninja.restoid.data.rest.adapter.RestAdapter;
import com.radicalninja.restoid.data.rest.client.RestClient;
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

    public void submitGET(String url) {
        client.getEndpoint(url, mCallback);
    }

    public void submitGET(String url, Map<String, String> query) {
        client.getEndpoint(url, query, mCallback);
    }

    public void submitPOST(String url) {
        client.postEndpoint(url, mCallback);
    }

    public void submitPOST(String url, Map<String, String> query) {
        client.postEndpoint(url, query, mCallback);
    }

    public void submitPATCH(String url) {
        client.patchEndpoint(url, mCallback);
    }

    public void submitPATCH(String url, Map<String, String> query) {
        client.patchEndpoint(url, query, mCallback);
    }

    public void submitDELETE(String url) {
        client.deleteEndpoint(url, mCallback);
    }

    public void submitDELETE(String url, Map<String, String> query) {
        client.deleteEndpoint(url, query, mCallback);
    }

}
