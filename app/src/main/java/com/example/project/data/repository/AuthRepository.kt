package com.example.project.data.repository

import com.example.project.data.firebase.FirebaseDatabaseManager
import com.example.project.data.model.Utilisateur

class AuthRepository {

    fun register(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        FirebaseAuthManager.register(email, password) { success, user, msg ->
            if (success && user != null) {
                FirebaseDatabaseManager.saveUser(Utilisateur(user.uid, user.email ?: ""))
            }
            onResult(success, msg)
        }
    }

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        FirebaseAuthManager.login(email, password) { success, _, msg ->
            onResult(success, msg)
        }
    }

    fun logout() = FirebaseAuthManager.logout()

    fun resetPassword(email: String, onResult: (Boolean, String?) -> Unit) {
        FirebaseAuthManager.resetPassword(email, onResult)
    }
}
