package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.UserData;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDTransactionService {

    String apiEndpoint = "/api/1.0/transactions";

    @POST(apiEndpoint)
    Call<UserData> createTransaction(@Body Product product);


}