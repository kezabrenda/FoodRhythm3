package com.example.foodrhythm3.network;

import com.example.foodrhythm3.models.ForkifySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("api/search?")
    Call<ForkifySearchResponse> getRecipes(@Query("q") String foodType);
}
