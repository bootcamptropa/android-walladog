package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.responses.RacesResponse;

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

public interface WDRacesService {

    String apiEndpoint = "/races";

    @GET(apiEndpoint)
    Call<RacesResponse> getMultiTask();

    @GET(apiEndpoint+"/{id}")
    Call<Product> getOneTask();

    @POST(apiEndpoint)
    Call<Product> postTask(@Body Race data);

    @PUT(apiEndpoint+"/{id}")
    Call<Product> putTask(@Path("id") String id, @Body Race data);

    @DELETE(apiEndpoint+"/{id}")
    Call deleteTask(@Path("id") String id);


}