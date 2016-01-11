package com.walladog.walladog.models.apiservices;

import android.preference.PreferenceActivity;
import android.util.Base64;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDOAuth {

    String credentials = AccessToken.clientID + ":" + AccessToken.clientSecret;
    String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

    String apiEndpoint = "/o/token/";
    @POST(apiEndpoint)
    @Headers("Content-Type: application/x-www-form-urlencoded;Authentication: Basic dUVBb3hWRVlPVm1wZzRaOElBeUNDRUl0bFhPOENmNEc3UlN1NjQ3ZDpaYzBKR3U1cFZVdDd6TnhyaFFhQ3ZpdDZ5ZHI0V3hNVkk0blhsdUY5R0JXZ0pWMHJiVWNSNXkydUJabDNUZ21MWXJ5YXNoSlJFcDlBaUlrYmZSVVZ2NUNkZDNuNlpYNFZhM2ZJMmNtdk13Y2dXUkZZbnJwN0s4WnR3a29wWHJoVg==")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

}