package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.UserData;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDUserDataService {
    String apiEndpoint = "/api/1.0/logins";
    @GET(apiEndpoint)
    Call<UserData> getUserData();
}