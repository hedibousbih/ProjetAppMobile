package com.example.project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bouton Login
        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9FC131) // Vert Figma
            ),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
        ) {
            Text("Login", color = Color.White, style = MaterialTheme.typography.bodyLarge )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lien "créer un compte"
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Vous n’avez pas de compte ?",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF9C8A8A)
            )
            Text(
                text = "créer un compte",
                style = MaterialTheme.typography.bodySmall.copy(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF9C8A8A),
                modifier = Modifier.clickable { onCreateAccountClick() }
            )
        }
    }
}

