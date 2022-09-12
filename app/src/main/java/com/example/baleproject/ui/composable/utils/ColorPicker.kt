package com.example.baleproject.ui.composable.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.baleproject.ui.composable.text.TextFieldCustomized

@Composable
fun ColorPicker(
    onColorChanged: (Color) -> Unit,
) {
    var r by remember { mutableStateOf("") }
    var g by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.Transparent) }

    LaunchedEffect(r, g, b) {
        color = (r + g + b).toColor().also(onColorChanged)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextFieldCustomized(
            value = r,
            onValueChange = { r = it },
            hint = "RR",
            modifier = Modifier
                .padding(start = 5.dp)
                .weight(1f)
        )
        TextFieldCustomized(
            value = g,
            onValueChange = { g = it },
            hint = "GG",
            modifier = Modifier
                .padding(start = 5.dp)
                .weight(1f)
        )
        TextFieldCustomized(
            value = b,
            onValueChange = { b = it },
            hint = "BB",
            modifier = Modifier
                .padding(start = 5.dp)
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Spacer(
            modifier = Modifier
                .padding(10.dp)
                .size(55.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(
                    color = color,
                )
        )
    }
}

private fun String.toColor(): Color {
    return try {
        if (length != 6) {
            throw IllegalStateException()
        }
        val colorInt = "#$this".toColorInt()
        Color(colorInt)
    } catch (e: Exception) {
        Color.Transparent
    }
}

@Preview(showBackground = true)
@Composable
fun ColorPickerPreview() {
    ColorPicker(onColorChanged = {})
}
