package com.example.baleproject.ui.composable.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baleproject.ui.theme.BaleProjectTheme
import com.example.baleproject.ui.theme.blue

@Composable
fun LoadingButton(
    text: String,
    isLoading: Boolean,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = blue,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .wrapContentHeight(align = Alignment.CenterVertically)
            .height(45.dp),
        enabled = isEnable,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(.7f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                color = Color.White,
            )
        } else {
            Text(
                text = text,
                fontSize = 17.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun LoadingButtonPreview() {
    BaleProjectTheme {
        Surface {
            var isLoading by remember { mutableStateOf(false) }

            LoadingButton(text = "Login", isLoading = isLoading) {
                isLoading = isLoading.not()
            }
        }
    }
}
