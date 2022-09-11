package com.example.baleproject.ui.composable.tapbor

import androidx.annotation.DrawableRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baleproject.R
import com.example.baleproject.ui.composable.image.Image
import com.example.baleproject.ui.theme.blue

@Composable
fun TabLayout(
    initialSelectedIndex: Int,
    tabs: List<TabItem>,
    onTabIndexChanged: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(initialSelectedIndex) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(percent = 50))
            .background(color = blue),
    ) {
        tabs.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            val backgroundColor = remember {
                Animatable(Color.Transparent)
            }
            val textColor = remember {
                Animatable(blue)
            }

            LaunchedEffect(isSelected) {
                val targetColor = if (isSelected) Color.White else blue
                backgroundColor.animateTo(targetValue = targetColor)
            }

            LaunchedEffect(isSelected) {
                val targetColor = if (isSelected) blue else Color.White
                textColor.animateTo(targetValue = targetColor)
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 6.dp, vertical = 3.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        color = backgroundColor.value,
                    )
                    .clickable(
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            selectedIndex = index
                            onTabIndexChanged(index)
                        }
                    )
                    .animateContentSize()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(visible = isSelected) {
                    Image(
                        id = item.icon,
                        colorFilter = blue,
                        modifier = Modifier.padding(end = 7.dp),
                    )
                }

                Text(text = item.title, color = textColor.value, fontSize = 23.sp)
            }

            if (index != tabs.lastIndex) {
                Spacer(modifier = Modifier.width(7.dp))
            }
        }
    }
}

@Stable
data class TabItem(
    val title: String,
    @DrawableRes val icon: Int,
)

@Preview
@Composable
fun TabLayoutPreview() = Surface {
    val tabs = listOf(
        TabItem("Filter", R.drawable.ic_filter),
        TabItem("Type", R.drawable.ic_add),
        TabItem("Sort", R.drawable.ic_sort),
    )
    TabLayout(initialSelectedIndex = 2, tabs = tabs) {

    }
}
