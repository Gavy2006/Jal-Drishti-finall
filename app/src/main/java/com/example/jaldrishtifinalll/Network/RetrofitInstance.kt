package com.example.jaldrishtifinalll.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "https://jal-drishti-production-bd14.up.railway.app/"

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

    val api: RainfallApi =
        retrofit.create(RainfallApi::class.java)
}