package com.example.baleproject.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.baleproject.R
import com.example.baleproject.data.model.Issue
import com.example.baleproject.ui.composable.dialog.LoginDialog
import com.example.baleproject.ui.composable.item.IssueItemCompose
import com.example.baleproject.ui.theme.blue

@Composable
fun Home(
    events: HomeEvents,
) {
    HomeState(events)
}

@Composable
private fun HomeState(
    events: HomeEvents,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val lazyPagingItems by remember { viewModel.currentFlow() }
    val lazyListState = rememberLazyListState()

    var loginDialogIsOpen by rememberSaveable { mutableStateOf(false) }
    val showFAB by remember { derivedStateOf { lazyListState.isScrollInProgress } }

    HomeContent(
        lazyListState = lazyListState,
        lazyPagingItems = lazyPagingItems.collectAsLazyPagingItems(),
        showFAB = showFAB,
        onCreateIssue = {
            if (viewModel.hasBeenLogin()) {
                events.navigateToFeedbackPage()
            } else {
                loginDialogIsOpen = true
            }
        },
        loginDialogIsOpen = loginDialogIsOpen,
        onDialogLoginButton = events::navigateToLoginPage,
        onLoginDialogDismissed = { loginDialogIsOpen = false }
    )
}

@Composable
private fun HomeContent(
    lazyListState: LazyListState,
    lazyPagingItems: LazyPagingItems<Issue>,
    showFAB: Boolean,
    onCreateIssue: () -> Unit,
    loginDialogIsOpen: Boolean,
    onDialogLoginButton: () -> Unit,
    onLoginDialogDismissed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateIssue,
                backgroundColor = blue,
                shape = RoundedCornerShape(30.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = Color.White,
                    )

                    AnimatedVisibility(visible = showFAB.not()) {
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Post an issue",
                            color = Color.White,
                            modifier = Modifier.padding(end = 4.dp),
                        )
                    }
                }
            }
        },
    ) { padding ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(padding),
        ) {
            items(items = lazyPagingItems, key = { it.id }) { issue ->
                issue?.let {
                    IssueItemCompose(issue = issue)
                }
            }
        }

        if (loginDialogIsOpen) {
            LoginDialog(
                onLoginButton = onDialogLoginButton,
                onDismissed = onLoginDialogDismissed,
            )
        }
    }
}