package com.example.baleproject.ui.composable.text.keybaordaction

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction

class NextKeyboardAction(
    private val onNext: KeyboardActionScope.() -> Unit
) : KeyboardAction {
    override fun option(): KeyboardOptions {
        return KeyboardOptions(
            imeAction = ImeAction.Next,
        )
    }

    override fun action(): KeyboardActions {
        return KeyboardActions(onNext = onNext)
    }
}