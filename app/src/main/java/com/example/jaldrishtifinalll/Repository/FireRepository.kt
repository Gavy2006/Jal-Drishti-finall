package com.example.jaldrishtifinalll.Repository

import com.example.jaldrishtifinalll.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    suspend fun saveUser(
        user: User
    ): Result<Unit> {

        return try {

            firestore
                .collection("users")
                .document(user.uid)
                .set(user)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    suspend fun getUser(
        uid: String
    ): Result<User> {

        return try {

            val snapshot = firestore
                .collection("users")
                .document(uid)
                .get()
                .await()

            val user =
                snapshot.toObject(User::class.java)

            if (user != null) {

                Result.success(user)

            } else {

                Result.failure(
                    Exception(
                        "User data not found"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    suspend fun updateCity(
        uid: String,
        city: String
    ): Result<Unit> {

        return try {

            firestore
                .collection("users")
                .document(uid)
                .update(
                    "city",
                    city
                )
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}