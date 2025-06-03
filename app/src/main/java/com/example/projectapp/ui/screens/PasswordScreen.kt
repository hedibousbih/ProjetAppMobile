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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.projectapp.ViewModel.UserInfoViewModel

@Composable
fun PasswordScreen(navController: NavController, viewModel: UserInfoViewModel) {
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

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
        Text(
            text = "Mot de passe",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFE0E0E0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF9FC131))
            )
        }

        AsyncImage(
            model = "https://media1.tenor.com/m/s1wb66uYSCMAAAAC/password-what-be-the-password.gif",
            imageLoader = imageLoader,
            contentDescription = "GIF mot de passe",
            modifier = Modifier
                .height(180.dp)
                .padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passet", color = Color(0xFF8C8C8C)) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9FC131),
                unfocusedBorderColor = Color(0xFF9FC131)
            )
        )

        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = it },
            label = { Text("Confirme Votre Mot de passe", color = Color(0xFF8C8C8C)) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9FC131),
                unfocusedBorderColor = Color(0xFF9FC131)
            )
        )

        error?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }

        Button(
            onClick = onClick@{
                if (viewModel.email.isBlank()) {
                    error = "Email manquant. Revenez à l'étape précédente."
                    return@onClick
                }

                if (password.length < 8 || !password.any { it.isUpperCase() } || !password.any { it.isDigit() }) {
                    error = "Min 8 caractères, 1 majuscule, 1 chiffre"
                    return@onClick
                }

                if (password != confirm) {
                    error = "Les mots de passe ne correspondent pas"
                    return@onClick
                }

                loading = true
                viewModel.registerUserWithEmail(viewModel.email, password) { success, message ->
                    if (success) {
                        viewModel.sendEmailVerification { verified, msg ->
                            loading = false
                            if (verified) {
                                navController.navigate("verification")
                            } else {
                                error = msg ?: "Erreur lors de l'envoi du mail"
                            }
                        }
                    } else {
                        loading = false
                        error = message
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
                Text("Continué", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
