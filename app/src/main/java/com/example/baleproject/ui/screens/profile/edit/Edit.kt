package com.example.baleproject.ui.screens.profile.edit

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
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

@Composable
fun Edit(
    events: EditEvents,
) {
    EditState(events)
}

@Composable
private fun EditState(
    events: EditEvents,
    viewModel: EditViewModel = hiltViewModel(),
) {
    val emailFocusReq = remember { FocusRequester() }

    val updateResult = viewModel.updateResult.collectAsState()

    LaunchedEffect(updateResult.value is Result.Success) {
        (updateResult.value as? Result.Success)?.data()?.let {
            events.onEditCompleted()
        }
    }

    val isLoading by remember { mutableStateOf(updateResult.value is Result.Loading) }

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

    EditContent(
        nameState = nameState,
        emailState = emailState,
        emailFocusReq = emailFocusReq,
        focusOnEmail = { emailFocusReq.requestFocus() },
        isLoading = isLoading,
        onUpdateButtonClicked = { viewModel.update() },
        onBackPressed = events::back,
    )
}

@Composable
private fun EditContent(
    nameState: TextFieldCustomizedState,
    emailState: TextFieldCustomizedState,
    emailFocusReq: FocusRequester,
    focusOnEmail: KeyboardActionScope.() -> Unit,
    isLoading: Boolean,
    onUpdateButtonClicked: () -> Unit,
    onBackPressed: () -> Unit,
) {
    BackButton(isEnable = isLoading.not(), onBackPressed = onBackPressed)

    CurveColumn {
        ActionBar()

        Spacer(modifier = Modifier.height(30.dp))

        NameTextField(nameState, focusOnEmail)

        Spacer(modifier = Modifier.height(15.dp))

        EmailTextField(
            emailState,
            emailFocusReq
        )

        Spacer(modifier = Modifier.height(40.dp))

        LoadingButton(
            text = stringResource(id = R.string.update),
            isLoading = isLoading,
            isEnable = nameState.isValid() and emailState.isValid(),
            onClick = onUpdateButtonClicked,
        )
    }
}

@Composable
private fun ActionBar() {
    TitleText(
        text = stringResource(id = R.string.edit),
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
    emailFocusReq: FocusRequester
) {
    Text(
        fontSize = 16.sp,
        text = stringResource(R.string.email),
        modifier = Modifier.fillMaxWidth()
    )
    TextFieldCustomized(
        state = state,
        hint = "example@mail.com",
        modifier = Modifier.focusRequester(emailFocusReq)
    )
}


