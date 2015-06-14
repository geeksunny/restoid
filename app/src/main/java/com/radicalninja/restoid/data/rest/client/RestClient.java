package com.radicalninja.restoid.data.rest.client;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface RestClient {

    @GET("/{endpoint}")
    public void getEndpoint(@Path("endpoint") final String endpoint, Callback<Object> callback);
}
