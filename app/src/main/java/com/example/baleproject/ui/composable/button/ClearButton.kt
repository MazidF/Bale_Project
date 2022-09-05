package com.example.baleproject.ui.composable.button

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.baleproject.R
import com.example.baleproject.ui.theme.BaleProjectTheme

@Composable
fun ClearButton(
    onClick: () -> Unit,
) {
    IconButton(
        id = R.drawable.ic_clear,
        onClick = onClick,
    )
}

@Preview
@Composable
fun ClearButtonPreview() {
    BaleProjectTheme {
        Surface {
            ClearButton {}
        }
    }
}