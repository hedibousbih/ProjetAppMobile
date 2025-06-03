

package com.example.projectapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.projectapp.ViewModel.UserInfoViewModel
import androidx.compose.material3.MaterialTheme
import com.cloudinary.android.MediaManager



class MainActivity : ComponentActivity() {

    private val userInfoViewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config: HashMap<String, String> = hashMapOf(
            "cloud_name" to "dzq47xuz2",
            "api_key" to "224613385229586",
            "api_secret" to "JwKcrh0hqmR4Fd1LOlHjsgT4Gvc"
        )
        MediaManager.init(this, config)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        modifier = Modifier.padding(innerPadding),
                        userInfoViewModel = userInfoViewModel
                    )
                }
            }
        }
    }
}


