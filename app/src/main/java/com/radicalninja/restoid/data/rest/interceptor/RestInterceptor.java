package com.radicalninja.restoid.data.rest.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit.RequestInterceptor;

public class RestInterceptor implements RequestInterceptor {

    private static RestInterceptor mInstance = null;

    private final Object mLock = new Object();
    private Map<String, String> mHeaders = new HashMap<String, String>();

    public static RestInterceptor getInstance() {
        if (mInstance == null) {
            mInstance = new RestInterceptor();
        }
        return mInstance;
    }

    public void addHeader(String name, String value) {
        synchronized (mLock) {
            mHeaders.put(name, value);
        }
    }

    public boolean removeHeader(String name) {
        boolean wasRemoved = false;
        synchronized (mLock) {
            if (mHeaders.containsKey(name)) {
                wasRemoved = true;
                mHeaders.remove(name);
            }
        }
        return wasRemoved;
    }

    public void removeAllHeaders() {
        synchronized (mLock) {
            mHeaders.clear();
        }
    }

    @Override
    public void intercept(RequestInterceptor.RequestFacade request) {
        Map<String, String> mapCopy = null;
        synchronized (mLock) {
            mapCopy = new HashMap<>(mHeaders);
        }
        Iterator iterator = mapCopy.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            request.addHeader((String) pair.getKey(), (String) pair.getValue());
        }
    }
}
