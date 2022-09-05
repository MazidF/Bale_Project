package com.example.baleproject.ui.screens.feedback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.baleproject.data.model.IssueType
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.UseCase
import com.example.baleproject.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var issueType by mutableStateOf(IssueType.Bug)

    private val _postResult = MutableStateFlow<Result<*>?>(null)
    val postResult get() = _postResult.asStateFlow()

    fun userName(): String {
        return useCase.getUserName()
    }

    fun descriptionChecker(): Boolean {
        return description.isBlank() or (description.length >= 40)
    }

    fun typeChecker(): Boolean {
        return issueType != IssueType.None
    }

    fun post() {
        launch {
            val flow = useCase.createIssue(
                title = title,
                description = description,
                type = issueType,
                labelIds = listOf(),
            )
            flow.collect {
                _postResult.emit(it)
            }
        }
    }
}