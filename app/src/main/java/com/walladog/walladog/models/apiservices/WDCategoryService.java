package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.responses.CategoryResponse;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDCategoryService {

    String apiEndpoint = "/api/1.0/categories";

    @GET(apiEndpoint)
    Call<List<Category>> getMultiTask();

    @GET(apiEndpoint+"/{id}")
    Call<Category> getOneTask();

    @POST(apiEndpoint)
    Call<Category> postTask(@Body Category data);

    @PUT(apiEndpoint+"/{id}")
    Call<Category> putTask(@Path("id") String id, @Body Category data);

    @DELETE(apiEndpoint+"/{id}")
    Call deleteTask(@Path("id") String id);


}