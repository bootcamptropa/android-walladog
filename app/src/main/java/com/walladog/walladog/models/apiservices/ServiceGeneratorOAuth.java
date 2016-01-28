package com.walladog.walladog.models.apiservices;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.walladog.walladog.WalladogApp;
import com.walladog.walladog.models.Detail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by hadock on 14/12/15.
 *
 */

public class ServiceGeneratorOAuth {

    public static final String API_BASE_URL = "http://api.walladog.com";
    private static final String TAG = ServiceGeneratorOAuth.class.getName();

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Detail.class, new GenericDeserializer<>(Detail.class))
            .create();

    private static OkHttpClient httpClient = new OkHttpClient()
            .setAuthenticator(new TokenAuthenticator());

    private static Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) throws UnsupportedEncodingException {
        return createService(serviceClass, null, null, null);
    }

    public static <S> S createService(Class<S> serviceClass,String clientId,String clientSecret) throws UnsupportedEncodingException {
        return createService(serviceClass, null, clientId, clientSecret);
    }

    public static <S> S createService(Class<S> serviceClass, final AccessToken token,String clientId,String clientSecret) throws UnsupportedEncodingException {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        httpClient.interceptors().clear();

        //Log interceptor
        httpClient.interceptors().add(logging);


        if (token != null) {
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "applicaton/json")
                            .header("Authorization", "Bearer "+token.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

        }else{

            if(clientId!=null && clientSecret!=null) {
                String credentials = clientId + ":" + clientSecret;
                final String basic = "Basic " + getBase64String(credentials);

                httpClient.interceptors().add(new Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", basic)
                                .header("Accept", "applicaton/json")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
            }else{
                final String actualToken = WalladogApp.context
                        .getSharedPreferences(WalladogApp.class.getSimpleName(), Context.MODE_PRIVATE)
                        .getString(AccessToken.OAUTH2_TOKEN,"");

                Log.v(TAG, "Token Actual:" + actualToken);

                httpClient.interceptors().add(new Interceptor() {
                    @Override
                    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", "Bearer "+actualToken)
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
            }
        }

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    public static String getBase64String(String value) throws UnsupportedEncodingException {
        return Base64.encodeToString(value.getBytes("UTF-8"), Base64.NO_WRAP);
    }
}