package com.example.jaldrishtifinalll.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RagRetrofitInstance {
    private const val Base_url = "http://10.0.2.2:8000/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(Base_url)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()


    val api: RagApi = retrofit.create(RagApi::class.java)
}