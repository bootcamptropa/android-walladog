package com.walladog.walladog.models.apiservices;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDOAuth {

    String apiEndpoint = "/o/token/";

    @FormUrlEncoded
    @POST(apiEndpoint)
    Call<AccessToken> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password
    );

}