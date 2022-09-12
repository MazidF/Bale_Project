package com.example.baleproject.ui.composable.utils

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baleproject.R
import com.example.baleproject.ui.composable.button.LoadingButton
import com.example.baleproject.ui.composable.text.TextFieldCustomized
import com.example.baleproject.ui.composable.wrapper.ItemCardCompose
import com.example.baleproject.ui.model.LabelItem

@Composable
fun LabelMaker(
    isLoading: Boolean,
    onClick: (LabelItem) -> Unit,
) {
    var label by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.Transparent) }
    val loading by remember { mutableStateOf(isLoading) }

    ItemCardCompose {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            TextFieldCustomized(
                value = label,
                label = stringResource(id = R.string.label),
                onValueChange = {
                    label = it
                },
                modifier = Modifier.width(150.dp),

                )

            Spacer(modifier = Modifier.height(10.dp))

            ColorPicker {
                color = it
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                LoadingButton(
                    text = stringResource(id = R.string.create),
                    isLoading = loading,
                    isEnable = (label.isNotBlank()) and (color != Color.Transparent),
                    matchWidth = false,
                    onClick = {
                        onClick(LabelItem(System.currentTimeMillis().toString(), label, color))
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun LabelMakerPreview() {
    LabelMaker(isLoading = false, onClick = {})
}
