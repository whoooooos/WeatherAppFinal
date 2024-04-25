package com.example.myntcodechallenge.util

import com.example.myntcodechallenge.Api.ApiInterface
import com.mkrdeveloper.weatherappexample.utils.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.Base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}