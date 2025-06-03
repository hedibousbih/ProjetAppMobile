package com.example.projectapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.screens.*
import com.example.projectapp.ViewModel.UserInfoViewModel

@Composable
fun Navigation(modifier: Modifier = Modifier, userInfoViewModel: UserInfoViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginFull") {
        composable("loginFull") {
            LoginScreen(navController)
        }
        composable("LoginInputs") {
            LoginScreenWithInputs(navController, viewModel = userInfoViewModel)
        }
        composable("profileName") {
            NameScreen(navController, viewModel = userInfoViewModel)
        }
        composable("profileEmail") {
            EmailScreen(navController, viewModel = userInfoViewModel)
        }
        composable("password") {
            PasswordScreen(navController, viewModel = userInfoViewModel)
        }
        composable("verification") {
            VerificationScreen(navController, viewModel = userInfoViewModel)
        }
        composable("loisirs") {
            LoisirsScreen(navController, viewModel = userInfoViewModel)
        }
        composable("home") {
            HomeScreen(navController = navController, viewModel = userInfoViewModel)
        }

        composable("profile") {
            ProfileScreen(navController, viewModel = userInfoViewModel)
        }
    }
}
