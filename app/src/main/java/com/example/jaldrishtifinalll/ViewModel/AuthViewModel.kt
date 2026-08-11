package com.example.jaldrishtifinalll.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jaldrishtifinalll.Repository.AuthRepository
import com.example.jaldrishtifinalll.Repository.FirestoreRepository
import com.example.jaldrishtifinalll.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val firestoreRepository = FirestoreRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message



    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {

        if (name.isBlank()) {
            _message.value = "Please enter your name"
            return
        }

        if (email.isBlank()) {
            _message.value = "Please enter your email"
            return
        }

        if (password.isBlank()) {
            _message.value = "Please enter your password"
            return
        }

        if (confirmPassword.isBlank()) {
            _message.value = "Please confirm your password"
            return
        }

        if (password != confirmPassword) {
            _message.value = "Passwords do not match"
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _message.value = ""

            val authResult = authRepository.register(
                email = email.trim(),
                password = password
            )

            authResult
                .onSuccess { uid ->

                    val user = User(
                        uid = uid,
                        name = name.trim(),
                        email = email.trim(),
                        city = "",
                        profilePhoto = ""
                    )

                    val firestoreResult =
                        firestoreRepository.saveUser(user)

                    firestoreResult
                        .onSuccess {

                            _isLoading.value = false

                            _message.value =
                                "Registration successful"

                            onSuccess()
                        }
                        .onFailure { error ->

                            _isLoading.value = false

                            _message.value =
                                "Firestore Error: ${
                                    error.message
                                }"
                        }
                }
                .onFailure { error ->

                    _isLoading.value = false

                    _message.value =
                        "Auth Error: ${
                            error.message
                        }"
                }
        }
    }



    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        if (email.isBlank()) {
            _message.value = "Please enter your email"
            return
        }

        if (password.isBlank()) {
            _message.value = "Please enter your password"
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _message.value = ""

            val result = authRepository.login(
                email = email.trim(),
                password = password
            )

            result
                .onSuccess {

                    _isLoading.value = false

                    _message.value =
                        "Login successful"

                    onSuccess()
                }
                .onFailure { error ->

                    _isLoading.value = false

                    _message.value =
                        "Login Error: ${
                            error.message
                        }"
                }
        }
    }


    fun logout() {

        authRepository.logout()

        _message.value = ""
    }


    fun getCurrentUser() =
        authRepository.getCurrentUser()


    fun clearMessage() {
        _message.value = ""
    }
}