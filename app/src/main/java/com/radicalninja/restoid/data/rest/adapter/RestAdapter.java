package com.radicalninja.restoid.data.rest.adapter;

import android.util.Pair;

import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.rest.client.RestClient;
import com.radicalninja.restoid.data.rest.interceptor.RestInterceptor;
import com.radicalninja.restoid.util.Ln;
import com.radicalninja.restoid.util.ResponseUtils;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okio.ByteString;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

public class RestAdapter {

    private static RestAdapter sInstance;
    private static RestInterceptor sRestInterceptor = null;

    private retrofit.RestAdapter mRestAdapter = null;
    private RestClient mRestClient = null;

    protected List<Pair<String, String>> mRequiredHeaders = new ArrayList<>();

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

        // Rest Adapter
        sRestInterceptor = RestInterceptor.getInstance();
        mRestAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(App.getEndpoint())
                .setClient(new DynamicTimeoutOkClient())
                .setRequestInterceptor(sRestInterceptor)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .setConverter(new StringConverter())
                .build();
        mRestClient = mRestAdapter.create(RestClient.class);

//        for(Pair<String, String> headerPair : mRequiredHeaders) {
//            sRestInterceptor.addHeader(headerPair.first, headerPair.second);
//        }
    }

    public class DynamicTimeoutOkClient extends OkClient {

        private OkHttpClient mClient;

        public DynamicTimeoutOkClient() {
            mClient = new OkHttpClient();
        }

        @Override
        public Response execute(Request request) throws IOException {
            mClient.setReadTimeout(App.getInstance().getTimeout(), TimeUnit.SECONDS);
            mClient.setReadTimeout(App.getInstance().getTimeout(), TimeUnit.SECONDS);

            return new OkClient(mClient).execute(request);
        }
    }

    public class StringConverter implements Converter {
        @Override public Object fromBody(TypedInput body, Type type) throws ConversionException {
            try {
                String string = ByteString.read(body.in(), (int) body.length()).utf8();
                try {
                    // If the string can get parsed as a JSON object, we can return it with indentation.
                    JSONObject json = new JSONObject(string);
                    String str = json.toString(4);
                    Ln.e(str);
                    return str;
                } catch (JSONException e) {
                    return string;
                }
            } catch (IOException e) {
                throw new ConversionException("Problem when convert string", e);
            }
        }

        @Override public TypedOutput toBody(Object object) {
            return new TypedString((String) object);
        }
    }

}
