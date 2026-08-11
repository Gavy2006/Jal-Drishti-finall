package com.example.jaldrishtifinalll.Navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.jaldrishtifinalll.Screen.HomePage
import com.example.jaldrishtifinalll.Screen.LoginPage
import com.example.jaldrishtifinalll.Screen.SignUpPage
import com.example.jaldrishtifinalll.Screen.WelcomePage
import com.example.jaldrishtifinalll.Screen.JalDrishtiBottomBar
import com.example.jaldrishtifinalll.Screen.TopBar

@Composable
fun Navigation() {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Top + Bottom bar in 3 screens par nahi dikhega
    val hideBars = currentRoute == "welcome" ||
            currentRoute == "login" ||
            currentRoute == "signup"

    Scaffold(
        topBar = {
            if (!hideBars) {
                TopBar()
            }
        },
        bottomBar = {
            if (!hideBars) {
                JalDrishtiBottomBar()
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = if (hideBars) {
                Modifier
            } else {
                Modifier.padding(paddingValues)
            }
        ) {

            composable("welcome") {
                WelcomePage(navController)
            }

            composable("login") {
                LoginPage(navController)
            }

            composable("signup") {
                SignUpPage(navController)
            }

            composable("home") {
                HomePage(navController)
            }

//            composable("harvest") {
//                HarvestPage(navController)
//            }
//
//            composable("map") {
//                MapPage(navController)
//            }
//
//            composable("profile") {
//                ProfilePage(navController)
//            }
        }
    }
}