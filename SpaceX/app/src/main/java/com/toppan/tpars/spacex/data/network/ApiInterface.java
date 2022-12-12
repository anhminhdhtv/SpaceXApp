package com.toppan.tpars.spacex.data.network;

import com.toppan.tpars.spacex.data.model.Launch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("launches/upcoming")
    Call<List<Launch>> getLaunchList();
}
