package com.example.baleproject.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.baleproject.R
import com.example.baleproject.data.result.Result
import com.example.baleproject.ui.composable.loading.LottieAnimationCustomized
import com.example.baleproject.ui.theme.blue
import kotlinx.coroutines.delay

@Composable
fun Splash(
    events: SplashEvents,
) {
    SplashState(events)
}

@Composable
fun SplashState(
    events: SplashEvents,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val result = viewModel.autoLoginState.collectAsState()
    var animationEnded by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.autoLogin()
        delay(2_000)
        animationEnded = true
    }

    LaunchedEffect(key1 = result, key2 = animationEnded) {
        if ((result.value !is Result.Loading) and animationEnded) {
            events.onSplashEnded()
        }
    }

    SplashContent(
        failed = result.value is Result.Fail,
        onRetry = viewModel::autoLogin,
    )
}

@Composable
fun SplashContent(
    failed: Boolean,
    onRetry: () -> Unit,
) {
    Box {
        LottieAnimationCustomized(
            id = R.raw.splash_animation,
            padding = 0.dp,
        )
        if (failed) {
            RetryBox(onRetry = onRetry)
        }
    }
}

@Composable
fun RetryBox(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .background(color = MaterialTheme.colors.surface.copy(0.5f)),
    ) {
        Text(
            text = stringResource(id = R.string.unable_to_connect),
            color = Color.Red.copy(alpha = 0.7f),
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
        )

        TextButton(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = blue,
            )
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                color = Color.White,
            )
        }
    }
}
