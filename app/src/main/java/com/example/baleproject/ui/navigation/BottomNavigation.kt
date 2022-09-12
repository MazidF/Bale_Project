package com.example.baleproject.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.baleproject.R
import com.example.baleproject.ui.navigation.actions.currentRoute
import com.example.baleproject.ui.navigation.actions.navigateToHome
import com.example.baleproject.ui.navigation.actions.navigateToLogin
import com.example.baleproject.ui.navigation.actions.navigateToProfile
import com.example.baleproject.ui.theme.blue
import com.example.baleproject.utils.logger

@Composable
fun BottomNavigation(
    navController: NavController,
    getLoginState: () -> Boolean,
) {
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRout = backstackEntry?.destination?.route ?: Destinations.Home.route

    androidx.compose.material.BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        BottomNavigationItem(
            selected = currentRout == Destinations.Home.route,
            iconId = R.drawable.ic_home,
            label = stringResource(id = R.string.home),
        ) {
            if (navController.currentRoute() == Destinations.ProfilePack.Profile.route) {
                navController.popBackStack()
            } else {
                navController.navigateToHome()
            }
        }

        BottomNavigationItem(
            selected = currentRout == Destinations.ProfilePack.Profile.route,
            iconId = R.drawable.ic_profile,
            label = stringResource(id = R.string.profile),
        ) {
            if (getLoginState()) {
                navController.navigateToProfile()
            } else {
                navController.navigateToLogin()
            }
        }
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    selected: Boolean,
    @DrawableRes iconId: Int,
    label: String,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        onClick = onClick,
        selected = selected,
        icon = {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
            )
        },
        label = {
            Text(text = label)
        },
        alwaysShowLabel = false,
        selectedContentColor = blue,
        unselectedContentColor = Color.Gray,
    )
}
