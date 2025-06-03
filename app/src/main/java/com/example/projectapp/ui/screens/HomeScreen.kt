package com.example.projectapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projectapp.R
import com.example.projectapp.ViewModel.UserInfoViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: UserInfoViewModel
) {
    val defaultImage = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
    val profileImage = remember { mutableStateOf(viewModel.photoUrl ?: defaultImage) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(profileImage.value)
                    .crossfade(true)
                    .build(),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate("profile")
                    }
            )

            IconButton(onClick = { /* paramètres */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }

        // Action buttons row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HomeActionButton("Play", R.drawable.img_2)
            HomeActionButton("Friends", R.drawable.img_3)
            HomeActionButton("Fun", R.drawable.img_4)
            HomeActionButton("Truth", R.drawable.img_5)
        }

        // Recent Players section
        Text("Recent Players", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RecentPlayer("Alice", "Loved playing Truth or Dare!", "2 hours ago")
            RecentPlayer("Bob", "Looking for friends to play.", "3 hours ago")
            RecentPlayer("Charlie", "Enjoys group activities!", "5 hours ago")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text("Start Activity", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Gaming")
            Text("Truth or Dare")
            Text("Group Activity")
        }

        Text(
            text = "Choose an option to get started!",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Invite Friends", fontWeight = FontWeight.Bold)

        Button(
            onClick = { /* to future search */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9FC131))
        ) {
            Text("Search Players", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HomeActionButton(label: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.size(64.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun RecentPlayer(name: String, desc: String, joined: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.img_1), // ou ic_placeholder
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.Bold)
            Text(desc, fontSize = 12.sp, color = Color.Gray)
        }
        Text("Joined $joined", fontSize = 12.sp, color = Color.Gray)
    }
}
