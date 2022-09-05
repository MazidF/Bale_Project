package com.example.baleproject.ui.composable.shimmer.utils

import androidx.compose.ui.graphics.Color

val ShimmerDefaultColors by lazy {
    listOf(
        Color.LightGray.copy(alpha = .9f),
        Color.LightGray.copy(alpha = .3f),
        Color.LightGray.copy(alpha = .9f),
    )
}

val ShimmerDefaultDuration by lazy {
    1_500
}

val ShimmerDefaultDelay by lazy {
    200
}