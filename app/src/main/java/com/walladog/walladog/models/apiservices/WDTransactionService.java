package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.Transaction;
import com.walladog.walladog.models.WDTransaction;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDTransactionService {

    String apiEndpoint = "/api/1.0/transactions/";

    @GET(apiEndpoint)
    Call<List<Transaction>> getTransactions();

    @POST(apiEndpoint)
    Call<Transaction> createTransaction(@Body WDTransaction tr);


}