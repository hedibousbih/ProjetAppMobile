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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectapp.ViewModel.UserInfoViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoisirsScreen(navController: NavController, viewModel: UserInfoViewModel) {
    val allOptions = listOf(
        "Gaming", "Foot", "Basket", "Sport", "Musique", "Netflix", "Lecture", "Voyage",
        "Cuisine", "Cinéma", "Fitness", "Techno", "Rap", "Rock", "Piano", "Guitare",
        "Théâtre", "Danse", "Mode", "Design", "Streaming", "Animé", "Manga",
        "League Of Legends", "Apex Legends", "Spotify", "Social Media", "Talking",
        "Films", "Animals", "Coding", "Podcast", "VR", "House Parties", "Nature"
    )

    var selected by remember { mutableStateOf(viewModel.hobbies) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre + retour
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
                text = "Loisirs",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )
        }

        // Barre de progression 100%
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFE0E0E0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF9FC131))
            )
        }

        // Chips de sélection
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            allOptions.forEach { option ->
                FilterChip(
                    selected = selected.contains(option),
                    onClick = {
                        selected = if (selected.contains(option)) {
                            selected - option
                        } else {
                            selected + option
                        }
                    },
                    label = { Text(option) },
                    shape = RoundedCornerShape(20.dp),
                    border = null,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF9FC131),
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = Color.Black,
                        selectedLeadingIconColor = Color.White
                    )
                )
            }
        }

        // Message erreur
        error?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }

        // Bouton continuer
        Button(
            onClick = onClick@{
                if (selected.isEmpty()) {
                    error = "Sélectionnez au moins un loisir"
                    return@onClick
                }
                viewModel.hobbies = selected
                viewModel.saveUserData { success, msg ->
                    if (success) {
                        navController.navigate("home") {
                            popUpTo("loginFull") { inclusive = true }
                        }
                    } else {
                        error = msg ?: "Erreur d'enregistrement"
                    }
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
