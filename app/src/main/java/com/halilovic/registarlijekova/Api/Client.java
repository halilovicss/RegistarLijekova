package com.halilovic.registarlijekova.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final String BASE_URL="https://api.farmaceut.ba/test/";
    private static Client client;
    private Retrofit retrofit;
    private Client(){


        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(300, TimeUnit.SECONDS)
                .callTimeout(300, TimeUnit.SECONDS)

                .writeTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

    }



    public static synchronized Client getInstance(){
        if (client==null){
            client = new Client();
        }
        return client;
    }

    public API getApi(){return retrofit.create(API.class);}
}
