package com.example.projectapp.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserInfoViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var photoUrl: String? = null
    var name: String = ""
    var email: String = ""
    var hobbies: List<String> = emptyList()

    fun registerUserWithEmail(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onResult(true, null)
                else onResult(false, task.exception?.message)
            }
    }

    fun sendEmailVerification(onResult: (Boolean, String?) -> Unit) {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) onResult(true, null)
                else onResult(false, task.exception?.message)
            }
    }

    fun saveUserData(onResult: (Boolean, String?) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(false, "Utilisateur non connecté")
            return
        }

        val userMap = hashMapOf(
            "name" to name,
            "email" to email,
            "hobbies" to hobbies
        )

        db.collection("users").document(uid)
            .set(userMap)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.message) }
    }
    fun loadUserData(onComplete: () -> Unit = {}, onError: (String) -> Unit = {}) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance().collection("users").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                name = doc.getString("name") ?: ""
                email = doc.getString("email") ?: ""
                hobbies = doc.get("hobbies") as? List<String> ?: emptyList()
                photoUrl = doc.getString("photoUrl")
                onComplete()
            }
            .addOnFailureListener { e -> onError(e.message ?: "Erreur Firestore") }
    }
}
