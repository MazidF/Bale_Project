package com.example.baleproject.ui.screens.signup

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
import com.example.baleproject.utils.logger

@Composable
fun Signup(events: SignupEvents) {
    SignupState(events = events)
}

@Composable
private fun SignupState(
    events: SignupEvents,
    viewModel: SignupViewModel = hiltViewModel(),
) {
    val emailFocusReq = remember { FocusRequester() }
    val passwordFocusReq = remember { FocusRequester() }

    val signUpResult = viewModel.signupResult.collectAsState()

    LaunchedEffect(key1 = signUpResult.value is Result.Success) {
        (signUpResult.value as? Result.Success)?.data()?.let { info ->
            events.onSignupCompleted(viewModel.email, viewModel.password)
            logger("onSignupCompleted")
        }
    }

    val isLoading by remember { mutableStateOf(signUpResult.value is Result.Loading) }

    val nameState = TextFieldCustomizedState(
        value = viewModel.name,
        isEnable = isLoading.not(),
        onValueChanged = { viewModel.name = it },
    )

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

    SignupContent(
        nameState = nameState,
        emailState = emailState,
        passwordState = passwordState,
        emailFocusReq = emailFocusReq,
        passwordFocusReq = passwordFocusReq,
        focusOnEmail = { emailFocusReq.requestFocus() },
        focusOnPassword = { passwordFocusReq.requestFocus() },
        isLoading = isLoading,
        onSignupButtonClicked = { viewModel.signUp() },
        onBackPressed = events::back,
        navigateToLogin = events::navigateToLogin,
    )
}

@Composable
private fun LoginButton(navigateToLogin: () -> Unit, isLoading: Boolean) {
    TextButton(onClick = navigateToLogin, enabled = isLoading.not()) {
        Text(
            text = stringResource(id = R.string.login),
            color = blue,
            fontSize = 18.5.sp,
        )
    }
}

@Composable
private fun SignupContent(
    nameState: TextFieldCustomizedState,
    emailState: TextFieldCustomizedState,
    passwordState: TextFieldCustomizedState,
    emailFocusReq: FocusRequester,
    passwordFocusReq: FocusRequester,
    focusOnEmail: KeyboardActionScope.() -> Unit,
    focusOnPassword: KeyboardActionScope.() -> Unit,
    isLoading: Boolean,
    onSignupButtonClicked: () -> Unit,
    onBackPressed: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    BackButton(isEnable = isLoading.not(), onBackPressed = onBackPressed)

    CurveColumn {
        ActionBar()

        Spacer(modifier = Modifier.height(30.dp))

        NameTextField(nameState, focusOnEmail)

        Spacer(modifier = Modifier.height(15.dp))

        EmailTextField(
            emailState,
            emailFocusReq,
            focusOnPassword
        )

        Spacer(modifier = Modifier.height(15.dp))

        PasswordField(
            passwordState,
            passwordFocusReq
        )

        Spacer(modifier = Modifier.height(40.dp))

        LoadingButton(
            text = stringResource(id = R.string.signin),
            isLoading = isLoading,
            isEnable = nameState.isValid() and emailState.isValid() and passwordState.isValid(),
            onClick = onSignupButtonClicked,
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(id = R.string.already_have_an_account),
            color = Color.Gray,
            fontSize = 18.5.sp,
        )

        LoginButton(navigateToLogin, isLoading)
    }
}

@Composable
private fun ActionBar() {
    TitleText(
        text = stringResource(id = R.string.signin),
        fontSize = 24.sp,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun NameTextField(
    state: TextFieldCustomizedState,
    focusOnEmail: KeyboardActionScope.() -> Unit
) {
    Text(fontSize = 16.sp, text = stringResource(R.string.name), modifier = Modifier.fillMaxWidth())
    TextFieldCustomized(
        state = state,
        hint = "Ali Alavi",
        keyboardAction = NextKeyboardAction(focusOnEmail)
    )
}

@Composable
private fun EmailTextField(
    state: TextFieldCustomizedState,
    emailFocusReq: FocusRequester,
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
        modifier = Modifier.focusRequester(emailFocusReq),
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
        hint = stringResource(R.string.at_least_8_characters),
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
