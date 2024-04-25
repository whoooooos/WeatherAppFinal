package com.example.myntcodechallenge.Api

import com.example.myntcodechallenge.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ivan Esguerra on 4/24/2024.
 **/
interface ApiInterface
{
    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("q") city : String,
        @Query("units") units : String,
        @Query("appid") apiKey : String,
    ): Response<Weather>
}