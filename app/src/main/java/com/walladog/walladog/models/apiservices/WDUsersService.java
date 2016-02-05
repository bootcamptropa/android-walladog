package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.UserData;
import com.walladog.walladog.models.UserSignIn;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDUsersService {

    String apiEndpoint = "/api/1.0/users/";

    @GET(apiEndpoint)
    Call<UserData> getUsers();

    @PUT(apiEndpoint+"/{id}")
    Call<UserData> updateUser(@Path("id") String idcustomer, @Body UserData data);

    @POST(apiEndpoint)
    Call<UserData> createUser(@Body UserSignIn data);


}