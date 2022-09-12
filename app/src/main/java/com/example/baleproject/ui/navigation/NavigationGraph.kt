package com.example.baleproject.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.ui.navigation.actions.*
import com.example.baleproject.ui.navigation.arguments.NavArgsType
import com.example.baleproject.ui.screens.detail.Detail
import com.example.baleproject.ui.screens.detail.DetailEvents
import com.example.baleproject.ui.screens.feedback.Feedback
import com.example.baleproject.ui.screens.feedback.FeedbackEvents
import com.example.baleproject.ui.screens.home.Home
import com.example.baleproject.ui.screens.home.HomeEvents
import com.example.baleproject.ui.screens.login.Login
import com.example.baleproject.ui.screens.login.LoginEvents
import com.example.baleproject.ui.screens.profile.Profile
import com.example.baleproject.ui.screens.profile.ProfileEvents
import com.example.baleproject.ui.screens.profile.edit.Edit
import com.example.baleproject.ui.screens.profile.edit.EditEvents
import com.example.baleproject.ui.screens.signup.Signup
import com.example.baleproject.ui.screens.signup.SignupEvents
import com.example.baleproject.ui.screens.splash.Splash
import com.example.baleproject.ui.screens.splash.SplashEvents

@Composable
fun Navigation(
    paddingValues: PaddingValues,
    navController: NavHostController,
    startDestinationRoute: String,
    onBackPressed: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
        modifier = Modifier.padding(paddingValues = paddingValues),
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
        ) {
            val issue = NavArgsType.getArgument(navController, Issue::class.java, "issue")
            issue?.let {
                Detail(createDetailEvents(navController, onBackPressed, it))
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

        profileGraph(navController, onBackPressed)
    }
}

fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    onBackPressed: () -> Unit,
) {
    navigation(
        startDestination = Destinations.ProfilePack.Profile.route,
        route = Destinations.ProfilePack::class.java.simpleName,
    ) {
        composable(Destinations.ProfilePack.Profile.route) {
            Profile(
                events = createProfileEvents(navController, onBackPressed),
            )
        }
        composable(Destinations.ProfilePack.Edit.route) {
            Edit(
                events = createEditEvents(navController, onBackPressed)
            )
        }
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
        composable(
            route = Destinations.LoginPack.Login.route + "?email={email},password={password}"
        ) { backStack ->
            val email = backStack.arguments?.getString("email")
            val password = backStack.arguments?.getString("password")
            Login(
                events = createLoginEvents(
                    navController, onBackPressed,
                    email ?: "",
                    password ?: ""
                )
            )
        }

        composable(Destinations.LoginPack.Signup.route) {
            Signup(
                events = createSignupEvents(
                    navController,
                    onBackPressed
                )
            )
        }
    }
}

private fun createLoginEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit,
    email: String,
    password: String,
): LoginEvents {
    return object : LoginEvents {
        override fun onLoginCompleted(info: UserInfo) {
            navController.popBackStack()
//            navController.navigateToHome(navController.currentDestination?.route /*Destinations.LoginPack.Login.route*/)
        }

        override fun navigateToSignupScreen() {
            navController.navigateToSignup(navController.currentDestination?.route /*Destinations.LoginPack.Login.route*/)
        }

        override fun getEmailAndPassword(): Pair<String, String> {
            return Pair(email, password)
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
        override fun onSignupCompleted(email: String, password: String) {
            navController.navigateToLogin(
                route = navController.currentDestination?.route /*Destinations.LoginPack.Signup.route*/,
                email = email,
                password = password
            )
        }

        override fun navigateToLogin() {
            navController.navigateToLogin(navController.currentDestination?.route /*Destinations.LoginPack.Signup.route*/)
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
            navController.navigateToHome(navController.currentDestination?.route /*Destinations.Splash.route*/)
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
        override fun navigateToDetailPage(issue: Issue) {
            navController.navigateToDetail(issue)
        }

        override fun navigateToFeedbackPage() {
            navController.navigateToFeedback()
        }

        override fun navigateToLoginPage() {
            navController.navigateToLogin()
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

fun createDetailEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit,
    issue: Issue,
): DetailEvents {
    return object : DetailEvents {
        override fun getIssueId(): String {
            return issue.id
        }

        override fun goToLoginPage() {
            navController.navigateToLogin()
        }

        override fun back() {
            onBackPressed()
        }
    }
}

fun createProfileEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit
): ProfileEvents {
    return object : ProfileEvents {
        override fun navigateToEdit() {
            navController.navigateToEdit(navController.currentRoute())
        }

        override fun onLogoutCompleted() {
            navController.navigateToHome(navController.currentDestination?.route)
        }

        override fun back() {
            onBackPressed()
        }
    }
}


fun createEditEvents(
    navController: NavHostController,
    onBackPressed: () -> Unit
): EditEvents {
    return object : EditEvents {
        override fun onEditCompleted() {
            back()
//            navController.navigateToProfile(navController.currentRoute())
        }

        override fun back() {
            navController.navigateToProfile(navController.currentRoute())
        }
    }
}
