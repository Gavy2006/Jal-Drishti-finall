package com.example.jaldrishtifinalll.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.jaldrishtifinalll.Repository.RagRepository
import com.example.jaldrishtifinalll.Repository.RainfallRepository

import com.example.jaldrishtifinalll.model.RagReportRequest
import com.example.jaldrishtifinalll.model.RagReportResponse
import com.example.jaldrishtifinalll.model.RainfallRequest
import com.example.jaldrishtifinalll.model.RainfallResponse

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RainfallViewModel : ViewModel() {

    private val rainfallRepository =
        RainfallRepository()

    private val ragRepository =
        RagRepository()


    // --------------------------------------------------
    // CALCULATION RESULT
    // --------------------------------------------------

    private val _result =
        MutableStateFlow<RainfallResponse?>(null)

    val result: StateFlow<RainfallResponse?> =
        _result


    // --------------------------------------------------
    // CALCULATION LOADING
    // --------------------------------------------------

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading


    // --------------------------------------------------
    // CALCULATION ERROR
    // --------------------------------------------------

    private val _error =
        MutableStateFlow("")

    val error: StateFlow<String> =
        _error


    // --------------------------------------------------
    // DETAILED REPORT
    // --------------------------------------------------

    private val _detailedReport =
        MutableStateFlow<RagReportResponse?>(null)

    val detailedReport:
            StateFlow<RagReportResponse?> =
        _detailedReport


    // --------------------------------------------------
    // REPORT LOADING
    // --------------------------------------------------

    private val _reportLoading =
        MutableStateFlow(false)

    val reportLoading:
            StateFlow<Boolean> =
        _reportLoading


    // --------------------------------------------------
    // REPORT ERROR
    // --------------------------------------------------

    private val _reportError =
        MutableStateFlow("")

    val reportError:
            StateFlow<String> =
        _reportError


    // --------------------------------------------------
    // CALCULATE + RAG
    // --------------------------------------------------

    fun asessRainwater(
        request: RainfallRequest
    ) {

        viewModelScope.launch {

            _isLoading.value = true

            _error.value = ""

            _result.value = null

            _detailedReport.value = null

            _reportError.value = ""


            // 1. CALCULATE

            val response =
                rainfallRepository
                    .assessRainwater(request)


            response.onSuccess { data ->

                _result.value = data


                // 2. CREATE RAG REQUEST

                val ragRequest =
                    RagReportRequest(

                        latitude =
                            request.lat ?: 0.0,

                        longitude =
                            request.lon ?: 0.0,

                        roof_area_m2 =
                            data.roof_area_m2,

                        roof_type =
                            data.roof_type,

                        annual_rainfall_mm =
                            data.annual_rainfall_mm,

                        harvestable_litres =
                            data.harvestable_litres,

                        runoff_coefficient =
                            data.runoff_coefficient_used
                    )


                // 3. GENERATE RAG REPORT

                _reportLoading.value = true


                val ragResponse =
                    ragRepository
                        .generateReport(
                            ragRequest
                        )


                ragResponse.onSuccess {

                    _detailedReport.value = it
                }


                ragResponse.onFailure {

                    _reportError.value =
                        it.message
                            ?: "Unable to generate detailed report"
                }


                _reportLoading.value = false
            }


            response.onFailure {

                _error.value =
                    it.message
                        ?: "Something went wrong"
            }


            _isLoading.value = false
        }
    }


    // --------------------------------------------------
    // CLEAR
    // --------------------------------------------------

    fun clearResult() {

        _result.value = null

        _error.value = ""

        _detailedReport.value = null

        _reportError.value = ""
    }
}