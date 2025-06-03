package com.example.projectapp.ui.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.projectapp.ViewModel.UserInfoViewModel

@Composable
fun EmailScreen(navController: NavController, viewModel: UserInfoViewModel) {
    var email by remember { mutableStateOf(viewModel.email) }
    var error by remember { mutableStateOf<String?>(null) }

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title with back arrow
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Retour",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = "Votre Email",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )
        }

        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFE0E0E0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF9FC131))
            )
        }

        // GIF
        AsyncImage(
            model = "https://media1.tenor.com/m/s1wb66uYSCMAAAAC/password-what-be-the-password.gif",
            imageLoader = imageLoader,
            contentDescription = "GIF email",
            modifier = Modifier
                .height(200.dp)
                .padding(vertical = 16.dp)
        )

        // Email input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color(0xFF8C8C8C)) },
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

        // Erreur si email invalide
        error?.let { Text(it, color = Color.Red) }

        // Bouton
        Button(
            onClick = {
                if (email.isBlank() || !email.contains("@")) {
                    error = "Adresse email invalide"
                } else {
                    viewModel.email = email.trim()
                    navController.navigate("password")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9FC131))
        ) {
            Text("Continué", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
