package com.example.baleproject.ui.screens.feedback

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.baleproject.R
import com.example.baleproject.data.model.IssueType
import com.example.baleproject.data.result.Result
import com.example.baleproject.ui.composable.button.BackButton
import com.example.baleproject.ui.composable.button.ClearButton
import com.example.baleproject.ui.composable.button.LoadingButton
import com.example.baleproject.ui.composable.item.LabelItemCompose
import com.example.baleproject.ui.composable.tapbor.TabItem
import com.example.baleproject.ui.composable.tapbor.TabLayout
import com.example.baleproject.ui.composable.text.DescriptionText
import com.example.baleproject.ui.composable.text.TextFieldCustomized
import com.example.baleproject.ui.composable.text.TextFieldCustomizedState
import com.example.baleproject.ui.composable.text.TitleText
import com.example.baleproject.ui.composable.text.keybaordaction.NextKeyboardAction
import com.example.baleproject.ui.composable.utils.LabelMaker
import com.example.baleproject.ui.composable.wrapper.CurveLazyColumn
import com.example.baleproject.ui.model.LabelItem
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun Feedback(events: FeedbackEvents) {
    FeedbackState(events)
}

@Composable
fun FeedbackState(
    events: FeedbackEvents,
    viewModel: FeedbackViewModel = hiltViewModel(),
) {
    val descriptionFocusReq = remember { FocusRequester() }

    val result = viewModel.postResult.collectAsState()
    val isLoading by remember { mutableStateOf(result.value is Result.Loading) }
    val issueType = remember { viewModel.issueType }

    LaunchedEffect(result.value is Result.Success) {
        (result.value as? Result.Success)?.let {
            events.onFeedbackPosted()
        }
    }

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
        focusOnDescription = { descriptionFocusReq.requestFocus() },
        initialIssueType = issueType,
        onIssueTypeChanged = { viewModel.issueType = IssueType.values()[it] },
        labels = viewModel.labels,
        onAddLabel = { viewModel.addLabel(it) },
        isLoading = isLoading,
        onPostClicked = { viewModel.post() },
        onBackPress = events::back,
    )
}

@Composable
fun FeedbackContent(
    userName: String,
    titleState: TextFieldCustomizedState,
    descriptionState: TextFieldCustomizedState,
    descriptionFocusReq: FocusRequester,
    focusOnDescription: KeyboardActionScope.() -> Unit,
    initialIssueType: IssueType,
    onIssueTypeChanged: (Int) -> Unit,
    labels: List<LabelItem>,
    onAddLabel: (LabelItem) -> Unit,
    isLoading: Boolean,
    onPostClicked: () -> Unit,
    onBackPress: () -> Unit,
) {
    BackButton(isEnable = isLoading.not(), onBackPressed = onBackPress)

    CurveLazyColumn {
        item(1) {
            TitleText(
                text = stringResource(id = R.string.feedback_maker_title),
                fontSize = 19.sp,
            )
        }

        SpacerItem(20.dp)

        item(2) {
            val type by remember { mutableStateOf(initialIssueType) }
            TypeField(type, onIssueTypeChanged)
        }

        SpacerItem(20.dp)

        item(3) {
            UsernameField(userName)
        }

        SpacerItem(15.dp)

        item(4) {
            TitleField(
                titleState, focusOnDescription
            )
        }

        SpacerItem(15.dp)

        item(5) {
            DescriptionField(
                descriptionState, descriptionFocusReq
            )
        }

        item(6) {
            LabelMaker(isLoading = false, onClick = onAddLabel)
        }

        SpacerItem(5.dp)

        item(7) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                labels.forEach {
                    LabelItemCompose(label = it)
                }
            }
        }

        SpacerItem(40.dp)

        item(8) {
            PostButton(
                isLoading = isLoading,
                isEnable = titleState.value.isNotBlank()
                        and descriptionState.value.isNotBlank()
                        and descriptionState.hasError.not(),
                onPostClicked = onPostClicked,
            )
        }
    }
}

private fun LazyListScope.SpacerItem(size: Dp) {
    item {
        Spacer(modifier = Modifier.height(size))
    }
}

@Composable
private fun TypeField(
    initialIssueType: IssueType,
    onIssueTypeChanged: (Int) -> Unit
) {
    TabLayout(
        initialSelectedIndex = initialIssueType.ordinal,
        tabs = listOf(
            TabItem(
                title = "Bug", icon = R.drawable.ic_bug,
            ),
            TabItem(
                title = "Suggestion", icon = R.drawable.ic_suggestion,
            )
        ),
        onTabIndexChanged = onIssueTypeChanged,
    )
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
private fun TitleField(
    state: TextFieldCustomizedState,
    focusOnDescription: KeyboardActionScope.() -> Unit,
) {
    Text(text = "Feedback Title")

    Spacer(modifier = Modifier.height(5.dp))

    DescriptionText(text = "Add a short description of feedback")

    TextFieldCustomized(
        state = state,
        hint = stringResource(id = R.string.title),
        trailingIcon = {
            ClearTrailingIcon(state.value, state.onValueChanged)
        },
        keyboardAction = NextKeyboardAction(focusOnDescription),
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

