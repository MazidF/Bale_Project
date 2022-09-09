package com.example.baleproject.ui.screens.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.baleproject.data.model.Comment
import com.example.baleproject.data.model.RawVote
import com.example.baleproject.data.result.Result
import com.example.baleproject.ui.composable.dialog.LoginDialog
import com.example.baleproject.ui.composable.item.CommentItemCompose
import com.example.baleproject.ui.composable.item.IssueItemCompose
import com.example.baleproject.ui.composable.item.ShimmerCommentItemCompose
import com.example.baleproject.ui.composable.item.ShimmerIssueItemCompose
import com.example.baleproject.ui.composable.loading.AppendingLoading
import com.example.baleproject.ui.composable.utils.CommentMaker
import com.example.baleproject.ui.composable.utils.context
import com.example.baleproject.ui.composable.wrapper.ItemCardCompose
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.utils.failed
import com.example.baleproject.utils.isAppending
import com.example.baleproject.utils.isRefreshing

@Composable
fun Detail(events: DetailEvents) {
    DetailState(events = events)
}

@Composable
private fun DetailState(
    events: DetailEvents,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val context = context()
    val lazyListState = rememberLazyListState()
    val pagingFlow by remember { viewModel.getFlow() }
    val lazyPagingItems = pagingFlow.collectAsLazyPagingItems()
    var isDialogOpen by remember { mutableStateOf(false) }

    val issueResult = viewModel.issueState.collectAsState()
    val votingResult = viewModel.votingState.collectAsState()
    val commentingResult = viewModel.commentingState.collectAsState()

    LaunchedEffect(true) {
        val issueId = events.getIssueId()
        viewModel.loadData(issueId)
    }

    LaunchedEffect(votingResult.value) {
        when (votingResult.value) {
            is Result.Fail -> {
                Toast.makeText(context, "Failed to vote", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(commentingResult.value) {
        when (commentingResult.value) {
            is Result.Fail -> {
                Toast.makeText(context, "Failed to comment", Toast.LENGTH_SHORT).show()
            }
            is Result.Success -> {
                lazyPagingItems.refresh()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        DetailContent(
            isVoting = votingResult.value is Result.Loading,
            onVote = { vote: RawVote ->
                if (viewModel.hasBeenLoggedIn()) {
                    viewModel.vote(vote)
                } else {
                    isDialogOpen = true
                }
            },
            isCreatingComment = commentingResult.value is Result.Loading,
            onCreateComment = { comment: String ->
                if (viewModel.hasBeenLoggedIn()) {
                    viewModel.createComment(comment)
                } else {
                    isDialogOpen = true
                }
            },
            itemIssue = (issueResult.value as? Result.Success)?.data(),
            lazyListState = lazyListState,
            lazyPagingItems = lazyPagingItems,
        )

        if (isDialogOpen) {
            LoginDialog(
                onLoginButton = events::goToLoginPage,
                onDismissed = {
                    isDialogOpen = false
                }
            )
        }
    }
}

@Composable
private fun DetailContent(
    isVoting: Boolean,
    itemIssue: IssueItem?,
    onVote: (RawVote) -> Unit,
    isCreatingComment: Boolean,
    onCreateComment: (String) -> Unit,
    lazyListState: LazyListState,
    lazyPagingItems: LazyPagingItems<Comment>,
) {
    LazyColumn(
        state = lazyListState,
    ) {
        item {
            if (itemIssue != null) {
                IssueItemCompose(
                    issue = itemIssue,
                    isLoading = isVoting,
                    onVote = onVote,
                )
            } else {
                ShimmerIssueItemCompose()
            }
        }

        item {
            CommentMaker(isLoading = isCreatingComment, onCreateButton = onCreateComment)
        }

        if (lazyPagingItems.loadState.isRefreshing()) { // use shimmer
            items(count = 5, key = { "shimmer$it" }) {
                ShimmerCommentItemCompose()
            }
        } else if (lazyPagingItems.loadState.failed()) {

        } else {
            if (lazyPagingItems.itemCount == 0) {
                item(
                    key = "empty_page"
                ) {
                    ItemCardCompose {
                        Text(
                            text = "No comment yet",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            items(items = lazyPagingItems, key = { it.id }) { comment ->
                comment?.let {
                    CommentItemCompose(comment = it)
                }
            }
            if (lazyPagingItems.loadState.isAppending()) {
                item {
                    AppendingLoading()
                }
            }
        }
    }
}
