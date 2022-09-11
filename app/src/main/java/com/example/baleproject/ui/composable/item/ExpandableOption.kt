package com.example.baleproject.ui.composable.item

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baleproject.R
import com.example.baleproject.ui.composable.image.Image
import com.example.baleproject.ui.theme.blue

@Composable
fun ExpandableOption(
    @DrawableRes iconId: Int,
    label: String,
    checkedOptionIndex: Int?,
    options: List<String>,
    isSelected: Boolean = false,
    selectedOptionIndex: (Int?) -> Unit
) {
    var isHighLighted by rememberSaveable { mutableStateOf(isSelected) }
    var checkedIndex by rememberSaveable { mutableStateOf(checkedOptionIndex) }

    Column(
        modifier = Modifier
            .animateContentSize()
            .padding(7.dp)
            .clickable(
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    isHighLighted = isHighLighted.not()
                }
            ),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(id = iconId, colorFilter = Color.Gray)

            Text(
                text = label,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(1f)
            )

            Crossfade(
                targetState = isHighLighted,
                animationSpec = tween(
                    durationMillis = 300,
                )
            ) {
                if (it) {
                    Image(id = R.drawable.ic_drop_up, colorFilter = blue)
                } else {
                    Image(id = R.drawable.ic_drop_down, colorFilter = Color.Gray)
                }
            }
        }
        if (isHighLighted) {
            Divider(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 3.dp)
                    .fillMaxWidth(),
                color = Color.Gray.copy(0.6f),
                thickness = 0.7.dp,
            )
            options.forEachIndexed { index, label ->
                SelectableItem(
                    label = label,
                    initialIsChecked = checkedIndex == index,
                    onCheckStateChanged = { isChecked ->
                        val newIndex = if (isChecked) {
                            index
                        } else {
                            null
                        }
                        checkedIndex = newIndex
                        selectedOptionIndex(newIndex)
                    }
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}
