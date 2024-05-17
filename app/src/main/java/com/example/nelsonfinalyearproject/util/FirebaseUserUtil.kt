package com.example.nelsonfinalyearproject.util

import android.net.Uri
import com.example.nelsonfinalyearproject.profile.UserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseUserUtil {

    suspend fun getFirebaseUserData(): UserModel? {
        val user = Firebase.auth.currentUser ?: return null
        return Firebase.firestore
            .collection("users")
            .document(user.uid)
            .get()
            .await()
            ?.toObject(UserModel::class.java)
    }

    fun updateUser(name: String? = null, photo: Uri? = null, cb: (Boolean) -> Unit) {
        val user = Firebase.auth.currentUser ?: return
        Firebase.firestore
            .collection("users")
            .document(user.uid)
            .set(
                buildMap<String, Any> {
                    if (!name.isNullOrEmpty()) {
                        put("name", name)
                    }
                    if (photo != null) {
                        put("photo", photo.toString())
                    }
                },
                SetOptions.merge()
            )
            .addOnCompleteListener {
                cb(it.isSuccessful)
            }
//        Firebase.auth.currentUser?.updateProfile(
//            userProfileChangeRequest {
//                if (!name.isNullOrEmpty()) {
//                    displayName = name
//                }
//                if (photo != null) {
//                    photoUri = photo
//                }
//            }
//        )?.addOnCompleteListener {
//            cb(it.isSuccessful)
//
//            Firebase.firestore.collection("users")
//
//        }
    }


}