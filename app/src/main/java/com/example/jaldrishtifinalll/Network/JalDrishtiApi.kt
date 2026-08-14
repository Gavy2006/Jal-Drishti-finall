package com.example.jaldrishtifinalll.Network

import com.example.jaldrishtifinalll.model.RainfallRequest
import com.example.jaldrishtifinalll.model.RainfallResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface JalDrishtiApi {

    @POST("assess")
    suspend fun assessRainWater(
        @Body request: RainfallRequest
    ): RainfallResponse
}