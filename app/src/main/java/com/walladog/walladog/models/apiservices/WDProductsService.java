package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.responses.ProductsResponse;

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

public interface WDProductsService {

    String apiEndpoint = "/products";

    @GET(apiEndpoint)
    Call<ProductsResponse> getMultiTask();

    @GET(apiEndpoint+"/{id}")
    Call<Product> getOneTask();

    @POST(apiEndpoint)
    Call<Product> postTask(@Body WDServices data);

    @PUT(apiEndpoint+"/{id}")
    Call<Product> putTask(@Path("id") String id, @Body Product data);

    @DELETE(apiEndpoint+"/{id}")
    Call deleteTask(@Path("id") String id);


}