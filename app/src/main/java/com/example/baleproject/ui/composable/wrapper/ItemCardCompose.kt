package com.example.baleproject.ui.composable.wrapper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun ItemCardCompose(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface,
        content = {
            content()
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick,
            )
    )
}

@Composable
fun ItemCardComposeWithColorLine(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface,
        content = {
/*            Surface(
                color = Color(0xFF3798CC),
                shape = RectangleShape,
                modifier = Modifier.height(6.dp),
            ) {}*/
            content()
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick,
            )
    )
}