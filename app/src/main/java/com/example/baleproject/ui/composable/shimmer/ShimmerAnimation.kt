package com.example.baleproject.ui.composable.shimmer

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShimmerAnimation(
    colors: List<Color>,
    duration: Int,
    delay: Int,
    context: @Composable (Brush) -> Unit,
) {
    val transition = rememberInfiniteTransition()
    val transitionAnimation = transition.animateFloat(
        initialValue = 10F,
        targetValue = 1000F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                delayMillis = delay,
                easing = LinearEasing,
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(
            x = transitionAnimation.value - 50,
            y = transitionAnimation.value - 50,
        ),
        end = Offset(
            x = transitionAnimation.value + 50,
            y = transitionAnimation.value + 50,
        ),
    )

    context(brush)
}