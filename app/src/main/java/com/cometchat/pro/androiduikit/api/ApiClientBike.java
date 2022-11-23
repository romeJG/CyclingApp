package com.cometchat.pro.androiduikit.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientBike {

    private static final String BASE_URL = "https://sinovaccycling.com/cycling-database-php/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
