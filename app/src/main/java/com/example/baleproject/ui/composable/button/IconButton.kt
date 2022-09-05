package com.example.baleproject.ui.composable.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    @DrawableRes id: Int,
    onClick: () -> Unit = {},
    colorFilter: Color = Color.Gray,
) {
    androidx.compose.material.IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape),
    ) {
        Image(
            painter = painterResource(id = id),
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorFilter),
        )
    }
}
