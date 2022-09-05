package com.example.baleproject.ui.composable.shimmer.shapes

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.baleproject.ui.composable.shimmer.ShimmerAnimation
import com.example.baleproject.ui.composable.shimmer.utils.ShimmerDefaultColors
import com.example.baleproject.ui.composable.shimmer.utils.ShimmerDefaultDelay
import com.example.baleproject.ui.composable.shimmer.utils.ShimmerDefaultDuration
import com.example.baleproject.ui.theme.BaleProjectTheme

@Composable
fun CircleShimmer(
    size: Dp,
    colors: List<Color> = ShimmerDefaultColors,
    duration: Int = ShimmerDefaultDuration,
    delay: Int = ShimmerDefaultDelay,
) {
    ShimmerAnimation(
        colors = colors,
        duration = duration,
        delay = delay,
    ) { brush ->
        Spacer(
            modifier = Modifier
                .size(size = size)
                .clip(CircleShape)
                .background(brush = brush),
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun CircleShimmerPreview() {
    BaleProjectTheme {
        Surface {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                CircleShimmer(size = 50.dp)
            }
        }
    }
}