package com.example.baleproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.baleproject.data.model.Issue
import com.example.baleproject.ui.navigation.BottomNavigation
import com.example.baleproject.ui.navigation.Destinations
import com.example.baleproject.ui.navigation.Navigation
import com.example.baleproject.ui.screens.home.Home
import com.example.baleproject.ui.screens.home.HomeEvents
import com.example.baleproject.ui.theme.BaleProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val route = navController.currentBackStackEntry?.destination?.route

            BaleProjectTheme {
                Scaffold(
                    backgroundColor = MaterialTheme.colors.background,
                    bottomBar = {
                        val backStack by navController.currentBackStackEntryAsState()
                        val currentRoute = backStack?.destination?.route
                        val isVisible = currentRoute == Destinations.Home.route
                                || currentRoute == Destinations.ProfilePack.Profile.route
                        AnimatedVisibility(
                            visible = isVisible,
                        ) {
                            BottomNavigation(
                                navController = navController,
                                getLoginState = viewModel::hasBeenLoggedIn,
                            )
                        }
                    }
                ) { padding ->
                    Navigation(
                        paddingValues = padding,
                        navController = navController,
                        startDestinationRoute = route ?: Destinations.Splash.route,
                        onBackPressed = this::onBackPressed,
                    )
                }
            }
        }
    }
}