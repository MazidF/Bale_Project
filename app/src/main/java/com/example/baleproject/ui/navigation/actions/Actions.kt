package com.example.baleproject.ui.navigation.actions

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.example.baleproject.data.model.Issue
import com.example.baleproject.ui.navigation.Destinations
import com.example.baleproject.ui.navigation.arguments.NavArgsType


private inline fun NavOptionsBuilder.popupTo(route: String?, isInclusive: Boolean) {
    route?.let {
        popUpTo(it) {
            inclusive = isInclusive
            saveState = true
        }
    }
}

fun NavController.navigateToHome(route: String? = null, isInclusive: Boolean = true) {
    navigate(Destinations.Home.route) {
        popupTo(route, isInclusive)
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToProfile(route: String? = null, isInclusive: Boolean = true) {
    navigate(Destinations.ProfilePack.Profile.route) {
        popupTo(route, isInclusive)
    }
}

fun NavController.navigateToEdit(route: String? = null, isInclusive: Boolean = true) {
    navigate(Destinations.ProfilePack.Edit.route) {
        popupTo(route, isInclusive)
    }
}

fun NavController.navigateToSignup(route: String? = null, isInclusive: Boolean = true) {
    navigate(Destinations.LoginPack.Signup.route) {
        popupTo(route, isInclusive)
    }
}

fun NavController.navigateToLogin(
    route: String? = null,
    isInclusive: Boolean = true,
    email: String = "",
    password: String = ""
) {
    navigate(Destinations.LoginPack.Login.route + "?email=$email,password=$password") {
        popupTo(route, isInclusive)
    }
}

fun NavController.navigateToDetail(issue: Issue) {
    NavArgsType.putArgument(this, Issue::class.java, issue, "issue")
    navigate(Destinations.Detail.route)
}

fun NavController.navigateToFeedback() {
    navigate(Destinations.Feedback.route)
}

fun NavController.currentRoute(): String? {
    return currentBackStackEntry?.destination?.route
}
