package com.example.baleproject.ui.screens.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.baleproject.data.model.Comment
import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.RawVote
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.UseCase
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.utils.launch
import com.example.baleproject.utils.toIssueItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    private var issue: IssueItem? = null

    private val _issueState = MutableStateFlow<Result<IssueItem>>(Result.loading())
    val issueState get() = _issueState.asStateFlow()

    private val _votingState = MutableStateFlow<Result<Unit>?>(null)
    val votingState get() = _votingState.asStateFlow()

    private val _commentingState = MutableStateFlow<Result<Unit>?>(null)
    val commentingState get() = _commentingState.asStateFlow()

    private var commentPageFlow: MutableState<Flow<PagingData<Comment>>> = mutableStateOf(flow { })

    fun getFlow(): MutableState<Flow<PagingData<Comment>>> {
        return commentPageFlow
    }

    private fun loadComments(issueId: String) {
        launch {
            commentPageFlow.value = useCase.loadComments(
                issueId = issueId,
            ).cachedIn(viewModelScope)
        }
    }

    fun createComment(comment: String) {
        issue?.let {
            launch {
                useCase.createComment(it.id, comment).collect {
                    _commentingState.emit(it)
                }
            }
        }
    }

    fun vote(vote: RawVote) {
        issue?.let {
            launch {
                useCase.vote(it.id, vote).collect {
                    _votingState.emit(it)
                    if (it is Result.Success) {
                        issue?.id?.run {
                            loadIssue(this)
                        }
                    }
                }
            }
        }
    }

    fun loadData(issueId: String) {
        loadIssue(issueId)
        loadComments(issueId)
    }

    private fun loadIssue(issueId: String) {
        launch {
            useCase.getIssue(issueId).collect {
                when (it) {
                    is Result.Success -> {
                        val issue = it.data()
                        loadLabels(issue)
                    }
                    else -> {
                        _issueState.emit(it.map { toIssueItem(emptyList()) })
                        issue = null
                    }
                }
            }
        }
    }

    private suspend fun loadLabels(issue: Issue) {
        val result = useCase.loadLabels(issue.labelIds)
        when (result) {
            is Result.Success -> {
                val issueItem = issue.toIssueItem(result.data())
                _issueState.emit(
                    Result.success(issueItem)
                )
                this.issue = issueItem
            }
            else -> {
                _issueState.emit(result.map {
                    throw IllegalStateException("Result must not be Success")
                })
            }
        }
    }

    fun hasBeenLoggedIn(): Boolean {
        return useCase.hasBeenLoggedIn()
    }
}
