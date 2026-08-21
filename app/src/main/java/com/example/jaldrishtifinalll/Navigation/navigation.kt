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
import com.example.jaldrishtifinalll.Screen.CalculateScreen
import com.example.jaldrishtifinalll.Screen.DetailedReportScreen

import com.example.jaldrishtifinalll.Screen.HomePage
import com.example.jaldrishtifinalll.Screen.LoginPage
import com.example.jaldrishtifinalll.Screen.SignUpPage
import com.example.jaldrishtifinalll.Screen.WelcomePage
import com.example.jaldrishtifinalll.Screen.JalDrishtiBottomBar
import com.example.jaldrishtifinalll.Screen.ProfilePage
import com.example.jaldrishtifinalll.Screen.TopBar
import com.example.jaldrishtifinalll.ViewModel.ProfileViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

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
                JalDrishtiBottomBar(navController = navController)
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

            composable("profile") {
                ProfilePage(navController )
            }

            composable("calculate") {
                CalculateScreen(navController)
            }

            composable("detailedReport") {
                DetailedReportScreen(
                    navController = navController
                )
            }

        }
    }
}