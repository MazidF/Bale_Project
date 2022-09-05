package com.example.baleproject.ui.composable.shimmer.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.baleproject.ui.composable.shimmer.ShimmerAnimation
import com.example.baleproject.ui.composable.shimmer.utils.ShimmerDefaultColors
import com.example.baleproject.ui.composable.shimmer.utils.ShimmerDefaultDelay
import com.example.baleproject.ui.composable.shimmer.utils.ShimmerDefaultDuration

@Composable
fun BoxShimmer(
    width: Dp? = null,
    height: Dp? = null,
    corner: Dp = 5.dp,
    colors: List<Color> = ShimmerDefaultColors,
    duration: Int = ShimmerDefaultDuration,
    delay: Int = ShimmerDefaultDelay,
    padding: Dp = 0.dp,
) {
    ShimmerAnimation(
        colors = colors,
        duration = duration,
        delay = delay,
    ) { brush ->
        Spacer(
            modifier = Modifier
                .then(
                    if (width == null) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier.width(width)
                    }
                )
                .then(
                    if (height == null) {
                        Modifier.fillMaxHeight()
                    } else {
                        Modifier.height(height)
                    }
                )
                .clip(RoundedCornerShape(corner))
                .background(brush = brush),
        )
    }
}