package com.example.baleproject.ui.composable.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import com.example.baleproject.R
import com.example.baleproject.ui.composable.image.Image
import com.example.baleproject.ui.theme.blue

@Composable
fun CheckBox(isSelected: Boolean) {
    AnimatedVisibility(
        visible = isSelected,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Image(id = R.drawable.ic_check, colorFilter = blue)
    }
}