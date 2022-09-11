package com.example.baleproject.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.baleproject.R
import com.example.baleproject.data.model.Issue
import com.example.baleproject.ui.composable.dialog.ListOptions
import com.example.baleproject.ui.composable.dialog.LoginDialog
import com.example.baleproject.ui.composable.image.Image
import com.example.baleproject.ui.composable.item.IssueItemCompose
import com.example.baleproject.ui.composable.loading.PagingLoadingState
import com.example.baleproject.ui.composable.text.TitleText
import com.example.baleproject.ui.theme.blue
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

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
    val isScrolling by remember { derivedStateOf { lazyListState.isScrollInProgress } }

    var optionDialogIsOpen by rememberSaveable { mutableStateOf(false) }
    var optionDialogTabIndex by rememberSaveable { mutableStateOf(-1) }
    val optionDialogState by remember { viewModel.optionState }

    HomeContent(
        lazyListState = lazyListState,
        lazyPagingItems = lazyPagingItems.collectAsLazyPagingItems(),
        isScrolling = isScrolling,
        onCreateIssue = {
            if (viewModel.hasBeenLoggedIn()) {
                events.navigateToFeedbackPage()
            } else {
                loginDialogIsOpen = true
            }
        },
        loginDialogIsOpen = loginDialogIsOpen,
        onDialogLoginButton = events::navigateToLoginPage,
        onLoginDialogDismissed = { loginDialogIsOpen = false },
        onItemClick = {
            events.navigateToDetailPage(it)
        },
        onTopBarTabClicked = {
            optionDialogTabIndex = it
            optionDialogIsOpen = true
        },
    )

    if (optionDialogIsOpen) {
        ListOptions(
            initialTabIndex = optionDialogTabIndex,
            initialState = optionDialogState,
            onDismiss = {
                optionDialogIsOpen = false
            }
        ) { newState ->
            viewModel.applyPagingOption(newState)
            optionDialogIsOpen = false
        }
    }
}

@Composable
private fun HomeContent(
    lazyListState: LazyListState,
    lazyPagingItems: LazyPagingItems<Issue>,
    isScrolling: Boolean,
    onCreateIssue: () -> Unit,
    loginDialogIsOpen: Boolean,
    onDialogLoginButton: () -> Unit,
    onLoginDialogDismissed: () -> Unit,
    onItemClick: (Issue) -> Unit,
    onTopBarTabClicked: (Int) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (lazyPagingItems.loadState.refresh is LoadState.NotLoading) {
                TopBar(
                    isScrolling.not(),
                    onFilterTabClicked = {
                        onTopBarTabClicked(0)
                    },
                    onSortTabClicked = {
                        onTopBarTabClicked(1)
                    }
                )
            }
        },
        floatingActionButton = {
            if (lazyPagingItems.loadState.refresh is LoadState.NotLoading) {
                FloatingActionButton(onCreateIssue, isScrolling.not())
            }
        },
    ) { padding ->
        FailedBox(lazyPagingItems)

        IssueList(lazyListState, lazyPagingItems, padding, onItemClick)

        LoadingDialog(loginDialogIsOpen, onDialogLoginButton, onLoginDialogDismissed)
    }
}

@Composable
private fun FailedBox(lazyPagingItems: LazyPagingItems<Issue>) {
    if (lazyPagingItems.loadState.refresh is LoadState.Error) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TitleText(text = "Failed to load items")
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { lazyPagingItems.retry() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = blue,
                        )
                    ) {
                        Text(text = stringResource(id = R.string.retry), color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun IssueList(
    lazyListState: LazyListState,
    lazyPagingItems: LazyPagingItems<Issue>,
    padding: PaddingValues,
    onItemClick: (Issue) -> Unit
) {
    PagingLoadingState(
        state = lazyListState,
        lazyPagingItems = lazyPagingItems,
        modifier = Modifier.padding(padding),
    ) {
        items(items = lazyPagingItems, key = { it.id }) { issue ->
            issue?.let {
                IssueItemCompose(issue = issue, onClick = { onItemClick(issue) })
            }
        }
    }
}

@Composable
fun TopBar(isScrolling: Boolean, onFilterTabClicked: () -> Unit, onSortTabClicked: () -> Unit) {
    AnimatedVisibility(visible = isScrolling) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.surface)
                .padding(horizontal = 10.dp),
        ) {
            TextButton(
                onClick = onFilterTabClicked,
                modifier = Modifier.weight(1f),
            ) {
                Image(
                    modifier = Modifier.padding(end = 5.dp),
                    id = R.drawable.ic_filter,
                    colorFilter = blue
                )
                Text(text = stringResource(R.string.filter), color = blue, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(10.dp))

            TextButton(
                onClick = onSortTabClicked,
                modifier = Modifier.weight(1f),
            ) {
                Image(
                    modifier = Modifier.padding(end = 5.dp),
                    id = R.drawable.ic_sort,
                    colorFilter = blue
                )
                Text(text = stringResource(R.string.sort), color = blue, fontSize = 20.sp)
            }
        }
    }
}

@Composable
private fun LoadingDialog(
    loginDialogIsOpen: Boolean,
    onDialogLoginButton: () -> Unit,
    onLoginDialogDismissed: () -> Unit
) {
    if (loginDialogIsOpen) {
        LoginDialog(
            onLoginButton = onDialogLoginButton,
            onDismissed = onLoginDialogDismissed,
        )
    }
}

@Composable
private fun FloatingActionButton(onCreateIssue: () -> Unit, showFAB: Boolean) {
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

            AnimatedVisibility(visible = showFAB) {
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Post an issue",
                    color = Color.White,
                    modifier = Modifier.padding(end = 4.dp),
                )
            }
        }
    }
}
