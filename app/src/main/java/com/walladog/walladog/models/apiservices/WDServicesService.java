package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.WDServices;

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

public interface WDServicesService {

    String apiEndpoint = "/services";

    @GET(apiEndpoint)
    Call<List<WDServices>> getMultiTask();

    @GET(apiEndpoint+"/{id}")
    Call<WDServices> getOneTask();

    @POST(apiEndpoint)
    Call<WDServices> postTask(@Body WDServices data);

    @PUT(apiEndpoint+"/{id}")
    Call<WDServices> putTask(@Path("id") String id, @Body WDServices data);

    @DELETE(apiEndpoint+"/{id}")
    Call deleteTask(@Path("id") String id);


}