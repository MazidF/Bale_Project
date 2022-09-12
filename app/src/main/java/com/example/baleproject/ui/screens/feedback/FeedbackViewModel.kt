package com.example.baleproject.ui.screens.feedback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.baleproject.data.model.IssueType
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.UseCase
import com.example.baleproject.ui.model.LabelItem
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
    var labels by mutableStateOf<List<LabelItem>>(emptyList())
        private set

    private val _postResult = MutableStateFlow<Result<*>?>(null)
    val postResult get() = _postResult.asStateFlow()

    private val _labels = MutableStateFlow<HashMap<String, LabelItem>>(hashMapOf())
    val label = _labels.asStateFlow()

    init {
        loadLabels()
    }

    private fun loadLabels() {
        launch {
            _labels.emit(useCase.getLabels())
        }
    }

    fun userName(): String {
        return useCase.getUserName()
    }

    fun descriptionChecker(): Boolean {
        return description.isBlank() or (description.length >= 40)
    }

    fun post() {
        launch {
            _postResult.emit(Result.loading<Unit>())
            val ids = useCase.createLabels(labels)
            val flow = useCase.createIssue(
                title = title,
                description = description,
                type = issueType,
                labelIds = ids,
            )
            flow.collect {
                _postResult.emit(it)
            }
        }
    }

    fun addLabel(label: LabelItem) {
        labels = labels + listOf(label)
    }
}