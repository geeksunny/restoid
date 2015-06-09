package com.radicalninja.restoid.data.rest.adapter;

import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radicalninja.restoid.data.rest.client.RestClient;
import com.radicalninja.restoid.data.rest.interceptor.AuthenticationInterceptor;

import java.util.ArrayList;
import java.util.List;

import retrofit.converter.GsonConverter;

public class RestAdapter {

    private static RestAdapter instance;

    private static AuthenticationInterceptor mAuthenticationInterceptor = null;
    private retrofit.RestAdapter mRestAdapter = null;
    private RestClient mRestClient = null;

    protected List<Pair<String, String>> mRequiredHeaders =
            new ArrayList<Pair<String, String>>() {
                {
                    add(new Pair<>("Accept", "application/json"));
                }
            };

    public RestAdapter() {
        //
    }

    public static RestAdapter getInstance() {
        if (null == instance) {
            instance = new RestAdapter();
        }
        return instance;
    }

    public RestClient getRestClient() {
        if (mRestClient == null) {
            createAdapter();
        }
        return mRestClient;
    }

    protected void createAdapter() {
        // GSON parser
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();

        // Rest Adapter
        mAuthenticationInterceptor = AuthenticationInterceptor.getInstance();
        mRestAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint("")
                .setRequestInterceptor(mAuthenticationInterceptor)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
        mRestClient = mRestAdapter.create(RestClient.class);

        for(Pair<String, String> headerPair : mRequiredHeaders) {
            mAuthenticationInterceptor.addHeader(headerPair.first, headerPair.second);
        }
    }
}
