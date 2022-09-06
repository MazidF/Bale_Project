package com.example.baleproject.ui.composable.item

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.baleproject.ui.model.LabelItem

@Composable
fun LabelItemCompose(label: LabelItem) {
    Surface(
        color = label.color,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(5.dp),
    ) {
        Text(
            text = label.name,
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            color = Color.White,
        )
    }
}

