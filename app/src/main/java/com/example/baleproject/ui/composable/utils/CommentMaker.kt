package com.example.baleproject.ui.composable.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baleproject.R
import com.example.baleproject.ui.composable.button.LoadingButton
import com.example.baleproject.ui.composable.text.TextFieldCustomized
import com.example.baleproject.ui.composable.wrapper.ItemCardCompose

@Composable
fun CommentMaker(
    isLoading: Boolean,
    onCreateButton: (String) -> Unit
) {
    var comment by remember { mutableStateOf("") }
    val loading by remember { mutableStateOf(isLoading) }
    val onClick by remember {
        mutableStateOf(
            {
                onCreateButton(comment.trim())
            }
        )
    }

    ItemCardCompose {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            TextFieldCustomized(
                value = comment,
                label = stringResource(id = R.string.comment),
                onValueChange = {
                    comment = it
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

@Preview(showBackground = true, name = "light")
@Preview(showBackground = true, name = "night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CommentMakerPreview() = Surface(
    modifier = Modifier.padding(10.dp),
    color = MaterialTheme.colors.background,
) {
    var isLoading by remember { mutableStateOf(false) }
    CommentMaker(isLoading = isLoading, onCreateButton = { isLoading = false })
}
