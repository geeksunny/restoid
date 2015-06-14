package com.radicalninja.restoid.data.rest.api;

import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ApiResponseEvent;
import com.radicalninja.restoid.data.rest.adapter.RestAdapter;
import com.radicalninja.restoid.data.rest.client.RestClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Api {

    private RestClient client = RestAdapter.getInstance().getRestClient();

    public void submitGET(String url) {
        client.getEndpoint(url, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                App.getOttoBus().post(new ApiResponseEvent(response.getBody().toString()));
            }

            @Override
            public void failure(RetrofitError error) {
                App.getOttoBus().post(new ApiResponseEvent(error.toString()));
            }
        });
    }

    public void submitPOST(String url) {
        client.postEndpoint(url, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                App.getOttoBus().post(new ApiResponseEvent(response.getBody().toString()));
            }

            @Override
            public void failure(RetrofitError error) {
                App.getOttoBus().post(new ApiResponseEvent(error.toString()));
            }
        });
    }

    public void submitPATCH(String url) {
        client.patchEndpoint(url, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                App.getOttoBus().post(new ApiResponseEvent(response.getBody().toString()));
            }

            @Override
            public void failure(RetrofitError error) {
                App.getOttoBus().post(new ApiResponseEvent(error.toString()));
            }
        });
    }

    public void submitDELETE(String url) {
        client.deleteEndpoint(url, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                App.getOttoBus().post(new ApiResponseEvent(response.getBody().toString()));
            }

            @Override
            public void failure(RetrofitError error) {
                App.getOttoBus().post(new ApiResponseEvent(error.toString()));
            }
        });
    }

}
