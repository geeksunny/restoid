package com.radicalninja.restoid.data.rest.client;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface RestClient {

    /* No extras */
    @GET("/{endpoint}")
    public void getEndpoint(@Path("endpoint") final String endpoint, Callback<Object> callback);

    @POST("/{endpoint}")
    public void postEndpoint(@Path("endpoint") final String endpoint, Callback<Object> callback);

    @PATCH("/{endpoint}")
    public void patchEndpoint(@Path("endpoint") final String endpoint, Callback<Object> callback);

    @DELETE("/{endpoint}")
    public void deleteEndpoint(@Path("endpoint") final String endpoint, Callback<Object> callback);

    /* with query parameters */
    @GET("/{endpoint}")
    public void getEndpoint(@Path("endpoint") final String endpoint, @QueryMap Map<String, String> queryParams, Callback<Object> callback);

    @POST("/{endpoint}")
    public void postEndpoint(@Path("endpoint") final String endpoint, @QueryMap Map<String, String> queryParams, Callback<Object> callback);

    @PATCH("/{endpoint}")
    public void patchEndpoint(@Path("endpoint") final String endpoint, @QueryMap Map<String, String> queryParams, Callback<Object> callback);

    @DELETE("/{endpoint}")
    public void deleteEndpoint(@Path("endpoint") final String endpoint, @QueryMap Map<String, String> queryParams, Callback<Object> callback);

    /* with body content */
    @POST("/{endpoint}")
    public void postEndpoint(@Path("endpoint") final String endpoint, @Body String body, Callback<Object> callback);

    @PATCH("/{endpoint}")
    public void patchEndpoint(@Path("endpoint") final String endpoint, @Body String body, Callback<Object> callback);

    /* with query and body content */
    @POST("/{endpoint}")
    public void postEndpoint(@Path("endpoint") final String endpoint, @QueryMap Map<String, String> queryParams, @Body String body, Callback<Object> callback);

    @PATCH("/{endpoint}")
    public void patchEndpoint(@Path("endpoint") final String endpoint, @QueryMap Map<String, String> queryParams, @Body String body, Callback<Object> callback);

}
