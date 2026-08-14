package com.example.jaldrishtifinalll.Repository

import com.example.jaldrishtifinalll.Network.RagRetrofitInstance
import com.example.jaldrishtifinalll.model.RagReportRequest
import com.example.jaldrishtifinalll.model.RagReportResponse

class RagRepository {

    private val api = RagRetrofitInstance.api

    suspend fun generateReport(
        request : RagReportRequest
    ) :Result<RagReportResponse>{


        return try {

          val response =  api.generateReport(request)

            Result.success(response)
        }
        catch(e : Exception) {
            Result.failure(e)
        }
    }
}