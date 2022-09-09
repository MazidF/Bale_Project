package com.example.baleproject.ui.composable.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun context(): Context {
    return LocalContext.current
}