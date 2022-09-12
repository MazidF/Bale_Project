package com.example.baleproject.ui.navigation

import androidx.navigation.NamedNavArgument

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
        route = "Detail",
        arguments = listOf(),
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

    enum class ProfilePack(
        val route: String,
    ) {
        Profile("Profile"),
        Edit("Edit");
    }
}