package com.example.jaldrishtifinalll.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{
    private const val Base_url =  "https://jal-drishti-production.up.railway.app/"


    val api : JalDrishtiApi by lazy {

        Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(JalDrishtiApi::class.java)
    }
}