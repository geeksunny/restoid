package com.radicalninja.restoid.application;

import android.app.Application;

import com.radicalninja.restoid.data.rest.DynamicEndpoint;
import com.squareup.otto.Bus;

public class App extends Application {

    private static App sInstance;
    private static Bus sOttoBus = new Bus();
    private static DynamicEndpoint sEndpoint = new DynamicEndpoint("http://www.google.com/");

    public App() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App getInstance() {
        return sInstance;
    }

    public static Bus getOttoBus() {
        return sOttoBus;
    }

    public static DynamicEndpoint getEndpoint() {
        return sEndpoint;
    }
}
