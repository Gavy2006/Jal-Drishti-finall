package com.example.jaldrishtifinalll.Network

import com.example.jaldrishtifinalll.model.RagReportRequest
import com.example.jaldrishtifinalll.model.RagReportResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface RagApi {

    @POST("api/report")
    suspend fun generateReport(

        @Body request : RagReportRequest
    ) : RagReportResponse
}