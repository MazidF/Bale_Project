package com.example.baleproject.ui.composable.text.keybaordaction

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

interface KeyboardAction {
    fun option(): KeyboardOptions
    fun action(): KeyboardActions

    companion object {
        val Default by lazy {
            object : KeyboardAction {
                override fun option(): KeyboardOptions {
                    return KeyboardOptions.Default
                }

                override fun action(): KeyboardActions {
                    return KeyboardActions.Default
                }
            }
        }
    }
}