package com.walladog.walladog.models.apiservices;

import com.walladog.walladog.models.Race;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by hadock on 14/12/15.
 *
 */

public interface WDRaceService {

    String apiEndpoint = "/api/1.0/races";

    @GET(apiEndpoint)
    Call<List<Race>> getRaces();

    @GET(apiEndpoint+"/{id}")
    Call<Race> getRace();

}