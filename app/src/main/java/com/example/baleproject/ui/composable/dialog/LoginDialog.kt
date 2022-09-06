package com.example.baleproject.ui.composable.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.baleproject.R
import com.example.baleproject.ui.composable.text.TitleText
import com.example.baleproject.ui.theme.blue

@Composable
fun LoginDialog(
    onLoginButton: () -> Unit,
    onDismissed: () -> Unit,
) {
    AlertDialog(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = onDismissed,
        title = {
            TitleText(text = "Login first")
        },
        text = {
            Text(
                text = "For creating issues or post a comment, you must login.",
            )
        },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(15.dp),
            ) {
                Button(
                    onClick = onDismissed,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Button(
                    onClick = {
                        onDismissed()
                        onLoginButton()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = blue,
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        color = Color.White,
                    )
                }
            }
        }
    )
}