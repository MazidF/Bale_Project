package com.example.baleproject.ui.screens.feedback

import androidx.compose.foundation.layout.*
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
import com.example.baleproject.ui.composable.button.ClearButton
import com.example.baleproject.ui.composable.button.LoadingButton
import com.example.baleproject.ui.composable.text.DescriptionText
import com.example.baleproject.ui.composable.text.TextFieldCustomized
import com.example.baleproject.ui.composable.text.TextFieldCustomizedState
import com.example.baleproject.ui.composable.text.TitleText

@Composable
fun Feedback(/*events: FeedbackEvents*/) {
    FeedbackState(object : FeedbackEvents {
        override fun back() {

        }

        override fun onFeedbackPosted() {

        }
    })
}

@Composable
fun FeedbackState(
    events: FeedbackEvents,
    viewModel: FeedbackViewModel = hiltViewModel(),
) {
    val descriptionFocusReq = remember { FocusRequester() }

    val result = viewModel.postResult.collectAsState()
    val isLoading by remember { mutableStateOf(result.value is Result.Loading) }

    val titleState = TextFieldCustomizedState(
        value = viewModel.title,
        isEnable = isLoading.not(),
        onValueChanged = { viewModel.title = it },
    )

    val descriptionState = TextFieldCustomizedState(
        value = viewModel.description,
        isEnable = isLoading.not(),
        hasError = viewModel.descriptionChecker().not(),
        onValueChanged = { viewModel.description = it },
    )

    FeedbackContent(
        userName = viewModel.userName(),
        titleState = titleState,
        descriptionState = descriptionState,
        descriptionFocusReq = descriptionFocusReq,
        isLoading = isLoading,
        onPostClicked = { viewModel.post() },
    )
}

@Composable
fun FeedbackContent(
    userName: String,
    titleState: TextFieldCustomizedState,
    descriptionState: TextFieldCustomizedState,
    descriptionFocusReq: FocusRequester,
    isLoading: Boolean,
    onPostClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 17.dp, vertical = 20.dp),
    ) {
        TitleText(
            text = stringResource(id = R.string.feedback_maker_title),
            fontSize = 19.sp,
        )

        Spacer(modifier = Modifier.height(20.dp))

        UsernameField(userName)

        Spacer(modifier = Modifier.height(15.dp))

        TitleField(titleState)

        Spacer(modifier = Modifier.height(15.dp))

        DescriptionField(descriptionState, descriptionFocusReq)

        Spacer(modifier = Modifier.height(40.dp))

        PostButton(
            isLoading = isLoading,
            isEnable = titleState.value.isNotBlank()
                    and descriptionState.value.isNotBlank()
                    and descriptionState.hasError.not(),
            onPostClicked = onPostClicked,
        )
    }
}

@Composable
private fun UsernameField(userName: String) {
    Text(text = stringResource(id = R.string.username))

    TextFieldCustomized(
        hint = "",
        value = userName,
        onValueChange = { throw IllegalStateException("Read Only text field!!") },
        readOnly = true,
        label = stringResource(id = R.string.username),
    )
}

@Composable
private fun TitleField(state: TextFieldCustomizedState) {
    Text(text = "Feedback Title")

    Spacer(modifier = Modifier.height(5.dp))

    DescriptionText(text = "Add a short description of feedback")

    TextFieldCustomized(
        state = state,
        hint = stringResource(id = R.string.title),
        trailingIcon = {
            ClearTrailingIcon(state.value, state.onValueChanged)
        },
    )
}

@Composable
private fun DescriptionField(
    state: TextFieldCustomizedState,
    descriptionFocusReq: FocusRequester,
) {
    Text(text = "Feedback Description")

    TextFieldCustomized(
        state = state,
        hint = stringResource(id = R.string.feedback_description),
        trailingIcon = {
            ClearTrailingIcon(state.value, state.onValueChanged)
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(descriptionFocusReq),
    )
}

@Composable
fun PostButton(isLoading: Boolean, isEnable: Boolean, onPostClicked: () -> Unit) {
    LoadingButton(
        text = stringResource(id = R.string.post),
        isLoading = isLoading,
        isEnable = isEnable,
        onClick = onPostClicked,
    )
}

@Composable
private fun ClearTrailingIcon(text: String, onTextChange: (String) -> Unit) {
    if (text.isNotBlank()) {
        ClearButton {
            onTextChange("")
        }
    }
}

