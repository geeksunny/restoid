package com.radicalninja.restoid.data.rest;

import retrofit.Endpoint;

public class DynamicEndpoint implements Endpoint {

    private String url;

    public DynamicEndpoint(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getName() {
        return "default";
    }
}
