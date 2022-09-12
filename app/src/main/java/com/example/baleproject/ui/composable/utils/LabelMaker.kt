package com.example.baleproject.ui.composable.utils

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.baleproject.R
import com.example.baleproject.ui.composable.button.LoadingButton
import com.example.baleproject.ui.composable.text.TextFieldCustomized
import com.example.baleproject.ui.composable.wrapper.ItemCardCompose

@Composable
fun LabelMaker(
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    var label by remember { mutableStateOf("") }
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
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                LoadingButton(
                    text = stringResource(id = R.string.create),
                    isLoading = loading,
                    matchWidth = false,
                    onClick = onClick,
                )
            }
        }
    }
}