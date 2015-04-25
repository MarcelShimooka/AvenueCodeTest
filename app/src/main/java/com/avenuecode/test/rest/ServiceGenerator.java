package com.avenuecode.test.rest;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Marcel on 24/04/2015 21:54
 *
 */
public class ServiceGenerator {

    private ServiceGenerator() {
    }

    /**
     * Create a REST adapter for a given interface
     */
    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(new OkClient(client));

        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }

}