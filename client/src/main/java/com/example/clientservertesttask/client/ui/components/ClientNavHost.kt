package com.example.clientservertesttask.client.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Destination(
    val route: String
) {
    Main("main"),
    Settings("settings")
}

@Composable
fun ClientNavHost(modifier: Modifier = Modifier) {
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
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}