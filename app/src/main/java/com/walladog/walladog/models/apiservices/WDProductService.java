package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.responses.ProductResponse;
import com.walladog.walladog.models.responses.ProductsResponse;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDProductService {

    String apiEndpoint = "/api/1.0/products/";

    @GET(apiEndpoint)
    Call<List<Product>> getProducts();

    @GET(apiEndpoint)
    Call<ProductResponse> getProductsPaginated(@Query("offset") int offset,@Query("limit") int limit);

    @GET(apiEndpoint)
    Call<ProductResponse> getSearchProductsPaginated(
            @Query("offset") String offset,
            @Query("limit") String limit,
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("race") String race,
            @Query("category") String category,
            @Query("distance") String distance
    );

    @GET(apiEndpoint+"/{id}/")
    Call<Product> getOneTask();

    @POST(apiEndpoint)
    Call<Product> postTask(@Body WDServices data);

    @PUT(apiEndpoint+"/{id}")
    Call<Product> putTask(@Path("id") String id, @Body Product data);

    @DELETE(apiEndpoint+"/{id}")
    Call deleteTask(@Path("id") String id);


}