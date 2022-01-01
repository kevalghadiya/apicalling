package com.example.apicalling.Utis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebApiClient {
    public static WebServices getInstance() {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(chain -> {
//            Request original = chain.request();
//            Request.Builder requestBuilder;
//            requestBuilder = original.newBuilder()
//                    .addHeader("Authorization", "Bearer " + PreferenceHelper.getString(Constants.Token, ""));
//            Request request = requestBuilder.build();
//            return chain.proceed(request);
//        });
            httpClient.addInterceptor(httpLoggingInterceptor);  // <-- this is the important line!

            httpClient.readTimeout(180, TimeUnit.SECONDS);
            httpClient.connectTimeout(180, TimeUnit.SECONDS);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(Keys.WEB_ADDRESS)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return mRetrofit.create(WebServices.class);
    }

    public static WebServices getInstance2() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder;
            requestBuilder = original.newBuilder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("Authorization", "key=AAAAkGnaWJM:APA91bH_A01scYkh2pCJs9MRz3PDEREpJZEOCHLGJ3J0qkL5HT4CVSe9MQUYz49RPcqfAOi7JJ6WO-4CSp4uizi3DtOj2WA53K_pU07v63TV9qufd0RTUKWmBTZCBR5GaEM3MGIJJboG");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        httpClient.addInterceptor(httpLoggingInterceptor);  // <-- this is the important line!

        httpClient.readTimeout(180, TimeUnit.SECONDS);
        httpClient.connectTimeout(180, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Keys.WEB_ADDRESS_FIREBASE)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return mRetrofit.create(WebServices.class);
    }          
}
