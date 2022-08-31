package com.example.baleproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baleproject.ui.screens.detail.Detail
import com.example.baleproject.ui.screens.home.Home
import com.example.baleproject.ui.screens.splash.Splash

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: Destinations,
    arguments: List<NamedNavArgument> = emptyList(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.name,
    ) {
        composable(
            route = Destinations.SplashScreen.name,
            arguments = arguments,
        ) {
            Splash()
        }

        composable(
            route = Destinations.Home.name,
            arguments = arguments,
        ) {
            Home()
        }

        composable(
            route = Destinations.Detail.name,
            arguments = arguments,
        ) {
            Detail()
        }
    }
}

