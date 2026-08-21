package com.example.jaldrishtifinalll.Repository

import com.example.jaldrishtifinalll.Network.RetrofitInstance
import com.example.jaldrishtifinalll.model.RainfallRequest
import com.example.jaldrishtifinalll.model.RainfallResponse

class RainfallRepository {

    private val api = RetrofitInstance.api

    suspend fun assessRainwater(
        request: RainfallRequest
    ): Result<RainfallResponse> {

        return try {

            val response =
                api.assessRainWater(request)

            Result.success(response)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}