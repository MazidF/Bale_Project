package com.example.baleproject.ui.composable.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baleproject.ui.composable.utils.CheckBox

@Composable
fun SelectableItem(
    label: String,
    initialIsChecked: Boolean = false,
    onCheckStateChanged: (Boolean) -> Unit
) {/*
    var isChecked by remember { mutableStateOf(initialIsChecked) }
*/
    Row(
        modifier = Modifier
            .padding(vertical = 3.dp, horizontal = 5.dp)
            .clickable(
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
//                    isChecked = isChecked.not()
                    onCheckStateChanged(initialIsChecked.not()/*isChecked*/)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(end = 5.dp)
                .padding(vertical = 5.dp),
            maxLines = 1,
        )
        CheckBox(isSelected = initialIsChecked/*isChecked*/)
    }
}