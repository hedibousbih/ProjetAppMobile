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
import com.google.firebase.auth.FirebaseAuth

@Composable
fun VerificationScreen(navController: NavController, viewModel: UserInfoViewModel) {
    var loading by remember { mutableStateOf(false) }
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
        // Titre avec retour
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
                text = "Email",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )
        }

        // Barre de progression complète
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFE0E0E0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF9FC131))
            )
        }

        // GIF
        AsyncImage(
            model = "https://media1.tenor.com/m/PJ44GA8oe14AAAAC/email-sent.gif",
            imageLoader = imageLoader,
            contentDescription = "GIF de vérification",
            modifier = Modifier
                .height(180.dp)
                .padding(vertical = 16.dp)
        )

        // Message
        Text(
            text = "Un e-mail de vérification a été envoyé.",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Lien pour renvoyer
        Text(
            text = "vous avez pas reçu le mail de vérification ?\nrenvoie le",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable {
                FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
            }
        )

        error?.let {
            Text(text = it, color = Color.Red)
        }

        Button(
            onClick = {
                loading = true
                FirebaseAuth.getInstance().currentUser?.reload()?.addOnCompleteListener {
                    loading = false
                    if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
                        navController.navigate("loisirs")
                    } else {
                        error = "Veuillez vérifier votre email."
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
                Text("Vérifié", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
