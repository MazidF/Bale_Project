package com.example.baleproject.ui.composable.loading

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationCustomized(
    @RawRes id: Int,
    padding: Dp = 130.dp,
    iterationCount: Int = Int.MAX_VALUE,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(id),
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterationCount,
    )
    LottieAnimation(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        composition = composition,
        progress = { progress },
    )
}

@Composable
fun LottieAnimationCustomized(
    @RawRes id: Int,
    padding: Int = 130,
    onAnimationEnded: () -> Unit,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(id),
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
    )

    LaunchedEffect(key1 = progress >= 1) {
        onAnimationEnded()
    }

    LottieAnimation(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding.dp),
        composition = composition,
        progress = { progress },
    )
}