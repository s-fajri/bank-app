package com.ayannah.bantenbank.data.remote;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.ayannah.bantenbank.BuildConfig;
import com.ayannah.bantenbank.data.remote.interceptor.RefreshTokenInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

public class RemoteService {

    private RefreshTokenInterceptor refreshTokenInterceptor;

    @Inject
    public RemoteService(RefreshTokenInterceptor refreshTokenInterceptor){
        this.refreshTokenInterceptor = refreshTokenInterceptor;
    }

    public void init(Application application){

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(refreshTokenInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        AndroidNetworking.initialize(application, okHttpClient);
        AndroidNetworking.setParserFactory(new GsonParserFactory(gson));
        if(BuildConfig.DEBUG){

            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.HEADERS);

        }
    }
}
