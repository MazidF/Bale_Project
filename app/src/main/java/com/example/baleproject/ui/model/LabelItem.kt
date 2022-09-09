package com.example.baleproject.ui.model

import androidx.compose.ui.graphics.Color

data class LabelItem(
    val id: String,
    val name: String,
    val color: Color,
) {
    companion object {
        fun empty(id: String) = LabelItem(
            id = id,
            name = "Unknown",
            color = Color.LightGray,
        )
    }
}