package com.example.baleproject.ui.screens.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.baleproject.R
import com.example.baleproject.data.result.Result
import com.example.baleproject.ui.composable.button.BackButton
import com.example.baleproject.ui.composable.button.LoadingButton
import com.example.baleproject.ui.composable.text.TextFieldCustomized
import com.example.baleproject.ui.composable.text.TextFieldCustomizedState
import com.example.baleproject.ui.composable.text.TitleText
import com.example.baleproject.ui.composable.text.keybaordaction.NextKeyboardAction
import com.example.baleproject.ui.composable.wrapper.CurveColumn
import com.example.baleproject.ui.theme.blue

@Composable
fun Login(events: LoginEvents) {
    LoginState(events)
}

@Composable
private fun LoginState(
    events: LoginEvents,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val passwordFocusReq = remember { FocusRequester() }

    val loginResult = viewModel.loginResult.collectAsState()
    val isLoading = loginResult.value is Result.Loading

    LaunchedEffect(loginResult.value is Result.Success) {
        (loginResult.value as? Result.Success)?.let {
            events.onLoginCompleted(it.data())
        }
    }

    val emailState = TextFieldCustomizedState(
        value = viewModel.email,
        isEnable = isLoading.not(),
        hasError = viewModel.emailChecker().not(),
        onValueChanged = { viewModel.email = it },
    )

    val passwordState = TextFieldCustomizedState(
        value = viewModel.password,
        isEnable = isLoading.not(),
        hasError = viewModel.passwordChecker().not(),
        onValueChanged = { viewModel.password = it },
    )

    LoginContent(
        emailState = emailState,
        passwordState = passwordState,
        passwordFocusReq = passwordFocusReq,
        focusOnPassword = { passwordFocusReq.requestFocus() },
        isLoading = isLoading,
        onBackPressed = events::back,
        onLoginButtonClicked = { viewModel.login() },
        navigateToSignup = events::navigateToSignupScreen,
    )
}

@Composable
private fun LoginContent(
    emailState: TextFieldCustomizedState,
    passwordState: TextFieldCustomizedState,
    passwordFocusReq: FocusRequester,
    focusOnPassword: KeyboardActionScope.() -> Unit,
    isLoading: Boolean,
    onBackPressed: () -> Unit,
    navigateToSignup: () -> Unit,
    onLoginButtonClicked: () -> Unit,
) {
    BackButton(isEnable = isLoading.not(), onBackPressed = onBackPressed)

    CurveColumn {
        ActionBar()

        Spacer(modifier = Modifier.height(30.dp))

        EmailTextField(
            emailState,
            focusOnPassword
        )

        Spacer(modifier = Modifier.height(15.dp))

        PasswordField(
            passwordState,
            passwordFocusReq
        )

        Spacer(modifier = Modifier.height(40.dp))

        LoadingButton(
            text = stringResource(id = R.string.login),
            isLoading = isLoading,
            isEnable = emailState.isValid() and passwordState.isValid(),
            onClick = onLoginButtonClicked,
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(id = R.string.do_not_have_an_account),
            color = Color.Gray,
            fontSize = 18.5.sp,
        )

        SignupButton(navigateToSignup, isLoading)
    }
}

@Composable
private fun SignupButton(navigateToSignup: () -> Unit, isLoading: Boolean) {
    TextButton(onClick = navigateToSignup, enabled = isLoading.not()) {
        Text(
            text = stringResource(id = R.string.signin),
            color = blue,
            fontSize = 18.5.sp,
        )
    }
}

@Composable
private fun ActionBar() {
    TitleText(
        text = stringResource(id = R.string.login),
        fontSize = 24.sp,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun EmailTextField(
    state: TextFieldCustomizedState,
    focusOnPassword: KeyboardActionScope.() -> Unit
) {
    Text(
        fontSize = 16.sp,
        text = stringResource(R.string.email),
        modifier = Modifier.fillMaxWidth()
    )
    TextFieldCustomized(
        state = state,
        hint = "example@mail.com",
        keyboardAction = NextKeyboardAction(focusOnPassword)
    )
}

@Composable
private fun PasswordField(
    state: TextFieldCustomizedState,
    passwordFocusReq: FocusRequester
) {
    var passwordIsVisible by remember { mutableStateOf(false) }


    Text(
        fontSize = 16.sp,
        text = stringResource(R.string.password),
        modifier = Modifier.fillMaxWidth()
    )
    TextFieldCustomized(
        state = state,
        hint = stringResource(R.string.your_password),
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordIsVisible = passwordIsVisible.not()
                },
            ) {
                Icon(
                    painter = painterResource(
                        id = if (passwordIsVisible) R.drawable.ic_invisible
                        else R.drawable.ic_visible
                    ),
                    contentDescription = null,
                )
            }
        },
        visualTransformation = if (passwordIsVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier.focusRequester(passwordFocusReq),
    )
}
