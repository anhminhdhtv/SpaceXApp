package com.toppan.tpars.spacex.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toppan.tpars.spacex.helper.DateDeserializer;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = " https://api.spacexdata.com/v4/";
    private static Retrofit INSTANCE;

    public synchronized static Retrofit getInstance(String baseUrl) {
        if (INSTANCE == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .create();
            INSTANCE = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return INSTANCE;
    }
}
