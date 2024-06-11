package com.example.clientservertesttask.server.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Destination(
    val route: String
) {
    Main("main"),
    Settings("settings"),
    Logs("logs")
}

@Composable
fun ServerNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Main.route) {
        composable(Destination.Main.route) {
            MainScreen(
                onNavigate = {
                     navController.navigate(it.route)
                },
                modifier = modifier
            )
        }
        composable(Destination.Settings.route) {
            SettingsScreen(modifier)
        }
        composable(Destination.Logs.route) {
            LogsScreen(modifier)
        }
    }
}