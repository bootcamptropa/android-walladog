package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.User;
import com.walladog.walladog.models.UserAuth;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface _WDLoginService {

    String apiEndpoint = "/auth";

    @GET(apiEndpoint)
    Call<List<User>> getMultiTask();

    @GET(apiEndpoint+"/{id}")
    Call<User> getOneTask();

    @POST(apiEndpoint)
    Call<List<User>> postTask(@Body UserAuth data);

    @PUT(apiEndpoint+"/{id}")
    Call<User> putTask(@Path("id") String id, @Body User data);

    @DELETE(apiEndpoint+"/{id}")
    Call deleteTask(@Path("id") String id);


}