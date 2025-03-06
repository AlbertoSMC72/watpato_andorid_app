package com.example.watpato.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watpato.addBook.presentation.CreateBookScreen
import com.example.watpato.addBook.presentation.CreateBookViewModel
import com.example.watpato.login.presentation.LoginScreen
import com.example.watpato.login.presentation.LoginViewModel
import com.example.watpato.register.presentation.RegisterScreen
import com.example.watpato.register.presentation.RegisterViewModel


@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login") {

        composable("Login") {
            LoginScreen(LoginViewModel()) { destination ->
                navController.navigate(destination)
            }
        }
        composable("Register") {
            RegisterScreen(RegisterViewModel()) { destination ->
                navController.navigate(destination)
            }
        }

        composable("AddBook") {
            CreateBookScreen(CreateBookViewModel()) { destination ->
                navController.navigate(destination)
            }
        }

    }
}
