package com.example.jaldrishtifinalll.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RagRetrofitInstance {

    private const val BASE_URL =
        "http://192.168.1.39:8000/"

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

    val api: RagApi =
        retrofit.create(RagApi::class.java)
}