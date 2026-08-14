package com.example.jaldrishtifinalll.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jaldrishtifinalll.Repository.RagRepository
import com.example.jaldrishtifinalll.model.RagReportRequest
import com.example.jaldrishtifinalll.model.RagReportResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RagViewModel : ViewModel(){

    private val repository = RagRepository()

    private val _report = MutableStateFlow<RagReportResponse?>(null)

    val report : StateFlow<RagReportResponse?> = _report

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error


    fun generateReport(
        request: RagReportRequest
    ) {

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = ""

            val response =
                repository.generateReport(request)

            response
                .onSuccess {
                    _report.value = it
                }
                .onFailure {
                    _error.value =
                        it.message
                            ?: "Unable to generate report"
                }

            _isLoading.value = false
        }
    }


    fun clearReport() {
        _report.value = null
        _error.value = ""
    }
}