package com.example.jaldrishtifinalll.Repository


import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    suspend fun register(
        email: String,
        password: String
    ): Result<String> {

        return try {

            val result = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid

            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(
                    Exception("User UID not found")
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<String> {

        return try {

            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid

            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(
                    Exception("User UID not found")
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser
}