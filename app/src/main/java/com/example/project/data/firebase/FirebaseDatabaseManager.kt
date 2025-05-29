package com.example.project.data.firebase


import com.google.firebase.database.FirebaseDatabase
import com.example.project.data.model.Utilisateur

object FirebaseDatabaseManager {
    private val db = FirebaseDatabase.getInstance().reference

    fun saveUser(user: Utilisateur) {
        db.child("users").child(user.uid).setValue(user)
    }
}

