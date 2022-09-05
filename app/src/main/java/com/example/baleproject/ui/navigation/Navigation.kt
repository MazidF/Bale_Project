package com.example.baleproject.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.ui.screens.detail.Detail
import com.example.baleproject.ui.screens.feedback.Feedback
import com.example.baleproject.ui.screens.feedback.FeedbackEvents
import com.example.baleproject.ui.screens.home.Home
import com.example.baleproject.ui.screens.home.HomeEvents
import com.example.baleproject.ui.screens.login.Login
import com.example.baleproject.ui.screens.login.LoginEvents
import com.example.baleproject.ui.screens.signup.Signup
import com.example.baleproject.ui.screens.signup.SignupEvents
import com.example.baleproject.ui.screens.splash.Splash
import com.example.baleproject.ui.screens.splash.SplashEvents
import com.example.baleproject.utils.getDetailRouteByIssue

@Composable
fun Navigation(
    navController: NavHostController,
    startDestinationRoute: String,
    onBackPressed: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        composable(
            route = Destinations.Splash.route,
            arguments = Destinations.Splash.arguments,
        ) {
            Splash(
                events = createSplashEvents(navController, onBackPressed),
            )
        }

        composable(
            route = Destinations.Home.route,
            arguments = Destinations.Home.arguments,
        ) {
            Home(
                events = createHomeEvents(navController, onBackPressed),
            )
        }

        composable(
            route = Destinations.Detail.route,
            arguments = Destinations.Detail.arguments,
        ) { backStack ->
            val issue = backStack.arguments?.getString("issueId")
            issue?.let {
                Detail(issue)
            }
        }

        composable(
            route = Destinations.Feedback.route,
            arguments = Destinations.Feedback.arguments,
        ) {
            Feedback(
                events = createFeedbackEvents(navController, onBackPressed, LocalContext.current),
            )
        }

        loginGraph(navController, onBackPressed)
    }
}

private fun NavGraphBuilder.loginGraph(
    navController: NavHostController,
    onBackPressed: () -> Unit,
) {
    navigation(
        startDestination = Destinations.LoginPack.Login.route,
        route = Destinations.LoginPack::class.java.simpleName,
    ) {
        composable(Destinations.LoginPack.Login.route) {
            Login(events = createLoginEvents(navController, onBackPressed))
        }
        composable(Destinations.LoginPack.Signup.route) {
            Signup(events = createSignupEvents(navController, onBackPressed))
        }
    }
}

private inline fun NavOptionsBuilder.popupTo(route: String?, isInclusive: Boolean) {
    route?.let {
        popUpTo(it) {
            inclusive = isInclusive
        }
    }
}

private fun NavController.navigateToHome(route: String? = null, isInclusive: Boolean = true) {
    navigate(Destinations.Home.route) {
        popupTo(route, isInclusive)
    }
}

private fun NavController.navigateToSignup(route: String? = null, isInclusive: Boolean = true) {
    navigate(Destinations.LoginPack.Signup.route) {
        popupTo(route, isInclusive)
    }
}

private fun NavController.navigateToLogin(route: String? = null, isInclusive: Boolean = true) {
    navigate(Destinations.LoginPack.Login.route) {
        popupTo(route, isInclusive)
    }
}

private fun NavController.navigateToDetail(issueItem: IssueItem) {
    navigate(getDetailRouteByIssue(issueItem))
}

private fun NavController.navigateToFeedback() {
    navigate(Destinations.Feedback.route)
}

private fun createLoginEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit
): LoginEvents {
    return object : LoginEvents {
        override fun onLoginCompleted(info: UserInfo) {
            navController.navigateToHome(Destinations.LoginPack.Login.route)
        }

        override fun navigateToSignupScreen() {
            navController.navigateToSignup(Destinations.Home.route, false)
        }

        override fun back() {
            onBackPressed()
        }
    }
}

private fun createSignupEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit
): SignupEvents {
    return object : SignupEvents {
        override fun onSignupCompleted() {
            navigateToLogin()
//            navController.navigateToHome(Destinations.LoginPack.Signup.route)
        }

        override fun navigateToLogin() {
            back()
            // TODO: check popup again
//            navController.navigateToLogin(Destinations.Home.route, false)
        }

        override fun back() {
            onBackPressed()
        }
    }
}

private fun createSplashEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit
): SplashEvents {
    return object : SplashEvents {
        override fun onSplashEnded() {
            navController.navigateToHome(Destinations.Splash.route)
        }

        override fun back() {
            onBackPressed()
        }
    }
}

private fun createHomeEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit
): HomeEvents {
    return object : HomeEvents {
        override fun navigateToDetailPage(issue: IssueItem) {
            navController.navigateToDetail(issue)
        }

        override fun navigateToFeedbackPage() {
            navController.navigateToFeedback()
        }

        override fun navigateToLoginPage() {
            navController.navigateToLogin(Destinations.Home.route, false)
        }

        override fun back() {
            onBackPressed()
        }
    }
}

private fun createFeedbackEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit,
    context: Context,
): FeedbackEvents {
    return object : FeedbackEvents {
        override fun onFeedbackPosted() {
            Toast.makeText(context, "Your Feedback posted", Toast.LENGTH_SHORT).show()
            back()
        }

        override fun back() {
            onBackPressed()
        }
    }
}
