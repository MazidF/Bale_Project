package com.example.baleproject.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.example.baleproject.ui.navigation.Destinations
import com.example.baleproject.ui.navigation.Navigation
import com.example.baleproject.ui.theme.BaleProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()

        setContent {
            val navController = rememberNavController()
            BaleProjectTheme {
                Surface {
                    Navigation(
                        navController = navController,
                        startDestinationRoute = Destinations.LoginPack::class.java.simpleName,
                        onBackPressed = this::onBackPressed,
                    )
                }
            }
        }
    }

    private fun setupView() {
        hideActionBar()
    }

    private fun hideActionBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}