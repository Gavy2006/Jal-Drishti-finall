package com.example.jaldrishtifinalll.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jaldrishtifinalll.Repository.RainfallRepository
import com.example.jaldrishtifinalll.model.RainfallRequest
import com.example.jaldrishtifinalll.model.RainfallResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RainfallViewModel : ViewModel(){

    private val repository = RainfallRepository()

    private val _result = MutableStateFlow<RainfallResponse?>(null)

    val result: StateFlow<RainfallResponse?> = _result

    private val _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow("")

    val error: StateFlow<String> = _error

    fun asessRainwater(
        request: RainfallRequest
    ){

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = ""
            _result.value = null


            val response = repository.assessRainwater(request)


            response
                .onSuccess {

                    _result.value = it
                }
                .onFailure {

                    _error.value =
                        it.message
                            ?: "Something went wrong"
                }

            _isLoading.value = false
        }
    }


    fun clearResult() {

        _result.value = null
        _error.value = ""
    }

}