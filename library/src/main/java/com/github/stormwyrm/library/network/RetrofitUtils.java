package com.github.stormwyrm.library.network;

import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;


public class RetrofitUtils {
    private static RetrofitUtils instance;
    private static final int TIME_CONNET = 3000;
    private OkHttpClient client;
    private Retrofit.Builder builder;

    private RetrofitUtils() {
        HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RetrofitUtils", "log: " + message);
            }
        };
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .writeTimeout(TIME_CONNET, TimeUnit.SECONDS)
                .readTimeout(TIME_CONNET, TimeUnit.SECONDS)
                .connectTimeout(TIME_CONNET, TimeUnit.SECONDS)
                .build();

        builder = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public <T> T createClass(Class<T> clazz, String baseUrl) {
        return builder.baseUrl(baseUrl)
                .build()
                .create(clazz);
    }

    public static RetrofitUtils getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtils.class) {
                if (instance == null) {
                    instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }
}
