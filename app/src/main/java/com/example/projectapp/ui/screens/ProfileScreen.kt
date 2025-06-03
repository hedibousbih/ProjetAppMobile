package com.example.projectapp.ui.screens

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cloudinary.android.MediaManager
import com.example.projectapp.ViewModel.UserInfoViewModel
import com.example.projectapp.utils.uploadToCloudinary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(navController: NavController, viewModel: UserInfoViewModel) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        it?.let { uri ->
            isUploading = true
            uploadToCloudinary(uri, context) { url ->
                if (url != null) {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@uploadToCloudinary
                    FirebaseFirestore.getInstance().collection("users").document(uid)
                        .update("photoUrl", url)
                        .addOnSuccessListener {
                            viewModel.photoUrl = url
                            isUploading = false
                        }
                        .addOnFailureListener {
                            error = "Erreur Firestore"
                            isUploading = false
                        }
                } else {
                    error = "Erreur upload Cloudinary"
                    isUploading = false
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Mon Profil", style = MaterialTheme.typography.titleLarge)

        AsyncImage(
            model = viewModel.photoUrl ?: "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { launcher.launch("image/*") }
        )

        if (isUploading) CircularProgressIndicator()
        if (error != null) Text(error!!, color = Color.Red)

        Text("Nom : ${viewModel.name}")
        Text("Email : ${viewModel.email}")
        Text("Loisirs : ${viewModel.hobbies.joinToString(", ")}")
    }
}
