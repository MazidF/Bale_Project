package com.example.baleproject.ui.composable.item

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.baleproject.data.model.Label

@Composable
fun LabelItemCompose(label: Label) {
    Surface(
        shape = RoundedCornerShape(5.dp),
//        color = Color(198, 205, 207, 255),
        color = Color(0xFFE7E6E6),
        modifier = Modifier.padding(5.dp),
    ) {
        Text(
            text = label.name,
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            color = label.color,
//            color = Color(17, 95, 146, 255),
        )
    }
}

