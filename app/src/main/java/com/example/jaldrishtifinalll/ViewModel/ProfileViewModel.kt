package com.example.jaldrishtifinalll.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jaldrishtifinalll.Repository.FirestoreRepository
import com.example.jaldrishtifinalll.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun loadUser() {

        val currentUser = auth.currentUser

        if (currentUser == null) {
            _message.value = "User not logged in"
            return
        }

        viewModelScope.launch {

            _isLoading.value = true

            val result = firestoreRepository.getUser(
                currentUser.uid
            )

            result
                .onSuccess { userData ->
                    _user.value = userData
                }
                .onFailure { error ->
                    _message.value =
                        error.message ?: "Failed to load profile"
                }

            _isLoading.value = false
        }
    }

    fun updateCity(city: String) {

        val currentUser = auth.currentUser

        if (currentUser == null) {
            _message.value = "User not logged in"
            return
        }

        if (city.isBlank()) {
            _message.value = "Please enter city"
            return
        }

        viewModelScope.launch {

            _isLoading.value = true

            val result = firestoreRepository.updateCity(
                uid = currentUser.uid,
                city = city.trim()
            )

            result
                .onSuccess {

                    _user.value = _user.value?.copy(
                        city = city.trim()
                    )

                    _message.value = "City updated successfully"
                }
                .onFailure { error ->

                    _message.value =
                        error.message ?: "Failed to update city"
                }

            _isLoading.value = false
        }
    }

    fun logout() {
        auth.signOut()
    }
}