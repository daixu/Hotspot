package com.jxs.hotspot.net.api.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jxs.hotspot.common.Constant.Url.API_SERVER_URL;

public class ApiRetrofit {

    private static Retrofit retrofit;

    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    public ApiRetrofit(OkHttpClient okHttpClient) {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_SERVER_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}