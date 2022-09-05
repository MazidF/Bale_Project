package com.example.baleproject.ui.composable.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.baleproject.R
import com.example.baleproject.ui.composable.color.surfaceColor

@Composable
fun BackButton(
    isEnable: Boolean,
    onBackPressed: () -> Unit,
) {
    androidx.compose.material.IconButton(
        onClick = onBackPressed,
        enabled = isEnable,
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(percent = 50),
            ),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .size(25.dp)
                .padding(2.dp),
            colorFilter = ColorFilter.tint(surfaceColor()),
        )
    }
}