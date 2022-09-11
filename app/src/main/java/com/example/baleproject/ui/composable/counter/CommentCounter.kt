package com.example.baleproject.ui.composable.counter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.baleproject.R

@Composable
fun CommentCounter(count: Int) {
    Surface {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_comment),
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = Modifier.alpha(.7f),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = count.toString()
            )
        }
    }
}