package com.walladog.walladog.models.apiservices;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.walladog.walladog.models.Detail;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by hadock on 14/12/15.
 *
 */

public class ServiceGeneratorOAuth {

    public static final String API_BASE_URL = "http://api.walladog.com";



    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Detail.class, new GenericDeserializer<>(Detail.class))
            .create();

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass, final AccessToken token,String clientId,String clientSecret) {
        if (token != null) {
            String credentials = clientId + ":" + clientSecret;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            httpClient.interceptors().clear();
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "applicaton/json")
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .header("Authorization", basic)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}