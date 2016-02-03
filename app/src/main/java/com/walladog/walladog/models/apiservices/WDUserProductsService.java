package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.Product;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDUserProductsService {
    String apiEndpoint = "/api/1.0/userProducts";
    @GET(apiEndpoint)
    Call<List<Product>> getUserProducts();
}