package com.walladog.walladog.models.apiservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.walladog.walladog.models.Detail;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by hadock on 14/12/15.
 *
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://api.develjitsu.com";
    //public static final String API_BASE_URL = "http://api.walladog.com";

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Detail.class, new GenericDeserializer<>(Detail.class))
            .create();

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}