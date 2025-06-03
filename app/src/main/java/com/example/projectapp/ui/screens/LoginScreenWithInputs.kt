package com.example.projectapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectapp.R
import com.example.projectapp.ViewModel.UserInfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginScreenWithInputs(
    navController: NavController,
    viewModel: UserInfoViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Logo",
            modifier = Modifier
                .size(180.dp)
                .padding(bottom = 32.dp)
        )

        // Email input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("email", color = Color(0xFF8C8C8C)) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9FC131),
                unfocusedBorderColor = Color(0xFF9FC131)
            )
        )

        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("password", color = Color(0xFF8C8C8C)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9FC131),
                unfocusedBorderColor = Color(0xFF9FC131)
            )
        )

        error?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                error = null
                loading = true
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.trim(), password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@addOnCompleteListener
                            FirebaseFirestore.getInstance().collection("users").document(uid)
                                .get()
                                .addOnSuccessListener { doc ->
                                    viewModel.name = doc.getString("name") ?: ""
                                    viewModel.email = doc.getString("email") ?: ""
                                    viewModel.hobbies = doc.get("hobbies") as? List<String> ?: emptyList()
                                    viewModel.photoUrl = doc.getString("photoUrl") // 🟢 Important
                                    loading = false
                                    navController.navigate("home") {
                                        popUpTo("loginFull") { inclusive = true }
                                    }
                                }
                                .addOnFailureListener {
                                    loading = false
                                    error = "Connecté mais impossible de charger les données."
                                }
                        } else {
                            loading = false
                            error = task.exception?.message ?: "Échec de la connexion"
                        }
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9FC131))
        ) {
            if (loading)
                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
            else
                Text("Login", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
