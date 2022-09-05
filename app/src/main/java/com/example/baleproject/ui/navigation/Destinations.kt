package com.example.baleproject.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class Destinations(
    val route: String,
    val arguments: List<NamedNavArgument>,
) {
    Splash(
        route = "Splash",
        arguments = emptyList(),
    ),
    Home(
        route = "Home",
        arguments = emptyList(),
    ),
    Detail(
        route = "Detail/{issueId}",
        arguments = listOf(
            navArgument("issueId") {
                type = NavType.StringType
            },
        ),
    ),
    Feedback(
        route = "Feedback",
        arguments = emptyList(),
    );

    enum class LoginPack(
        val route: String,
    ) {
        Login("Login"),
        Signup("Signup");
    }
}