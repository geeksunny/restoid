package com.radicalninja.restoid.data.rest.adapter;

import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.rest.LenientGsonConverter;
import com.radicalninja.restoid.data.rest.client.RestClient;
import com.radicalninja.restoid.data.rest.interceptor.RestInterceptor;

import java.util.ArrayList;
import java.util.List;

public class RestAdapter {

    private static RestAdapter sInstance;
    private static RestInterceptor sRestInterceptor = null;

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
        if (null == sInstance) {
            sInstance = new RestAdapter();
        }
        return sInstance;
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
        sRestInterceptor = RestInterceptor.getInstance();
        mRestAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(App.getEndpoint())
                .setRequestInterceptor(sRestInterceptor)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                //.setConverter(new GsonConverter(gson))
                .setConverter(new LenientGsonConverter(gson))
                .build();
        mRestClient = mRestAdapter.create(RestClient.class);

        for(Pair<String, String> headerPair : mRequiredHeaders) {
            sRestInterceptor.addHeader(headerPair.first, headerPair.second);
        }
    }
}
