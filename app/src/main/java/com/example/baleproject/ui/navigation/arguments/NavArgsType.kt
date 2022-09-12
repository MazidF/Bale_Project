package com.example.baleproject.ui.navigation.arguments

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import java.io.Serializable


class NavArgsType<T : Serializable> private constructor(
    private val clazz: Class<T>,
) : NavType<T>(false) {
    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getSerializable(key) as? T
    }

    override fun parseValue(value: String): T {
        return Gson().fromJson(value, clazz)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putSerializable(key, value)
    }

    companion object {
        fun <T : Serializable> navArguments(
            clazz: Class<T>,
            key: String? = null
        ): NamedNavArgument {
            return navArgument(key ?: clazz.simpleName) {
                type = NavArgsType(clazz)
            }
        }

        fun <T : Serializable> putArgument(
            navController: NavController,
            clazz: Class<T>,
            value: T,
            key: String? = null
        ) {
            navController.currentBackStackEntry?.arguments?.putSerializable(
                key ?: clazz.simpleName,
                value
            )
        }

        fun <T : Serializable> getArgument(
            navController: NavController,
            clazz: Class<T>,
            key: String? = null,
        ): T? {
            return navController.previousBackStackEntry?.arguments?.getSerializable(
                key ?: clazz.simpleName
            ) as? T
        }
    }
}