package com.radicalninja.restoid.data.event;

import java.util.Map;

public class ApiResponseEvent {

    public boolean success;
    public Map<String, String> headers;
    public String body;

    public ApiResponseEvent(String error) {
        this.body = error;
        success = false;
    }

    public ApiResponseEvent(Map<String, String> headers, String body) {
        this.headers = headers;
        this.body = body;
        success = true;
    }

}
