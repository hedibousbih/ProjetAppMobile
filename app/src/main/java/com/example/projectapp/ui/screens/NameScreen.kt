package com.example.projectapp.ui.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.projectapp.ViewModel.UserInfoViewModel

@Composable
fun NameScreen(navController: NavController, viewModel: UserInfoViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre
        Text(
            text = "Votre Nom",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )

        // Barre de progression stylisée
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFE0E0E0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF9FC131))
            )
        }

        // Image GIF
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        AsyncImage(
            model = "https://media.tenor.com/OlEmizIGQeoAAAAi/whats-your-name-ike-broflovski.gif",
            contentDescription = "GIF what's your name",
            imageLoader = imageLoader,
            modifier = Modifier
                .height(180.dp)
                .padding(top = 8.dp, bottom = 16.dp)
        )

        // Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom", color = Color(0xFF8C8C8C)) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9FC131),
                unfocusedBorderColor = Color(0xFF9FC131)
            )
        )

        // Bouton
        Button(
            onClick = {
                viewModel.name = name
                navController.navigate("profileEmail")
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
