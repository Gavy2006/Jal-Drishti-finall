package com.example.jaldrishtifinalll.Network

import androidx.annotation.BoolRes
import com.example.jaldrishtifinalll.model.RainfallRequest
import com.example.jaldrishtifinalll.model.RainfallResponse
import retrofit2.http.POST
import retrofit2.http.Body


interface JalDrishtiApi {

    @POST("assess")
    suspend fun assessRainWater(
        @Body  request: RainfallRequest
    ): RainfallResponse

}