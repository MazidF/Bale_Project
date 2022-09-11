package com.example.baleproject.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.baleproject.data.model.Issue
import com.example.baleproject.domain.UseCase
import com.example.baleproject.ui.composable.dialog.ListOptionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    private val _optionState: MutableState<ListOptionsState> = mutableStateOf(ListOptionsState())
    val optionState: State<ListOptionsState> = _optionState

    private var currentFlow: MutableState<Flow<PagingData<Issue>>> = mutableStateOf(flow { })


    init {
        loadIssues()
    }

    private fun loadIssues() {
        val state = optionState.value
        currentFlow.value = useCase.loadIssues(
            status = state.currentIssueStatus,
            type = state.currentIssueType,
            sortBy = state.currentSortBy,
            sortType = state.currentSortType,
        ).cachedIn(viewModelScope)
    }

    fun hasBeenLoggedIn(): Boolean {
        return useCase.hasBeenLoggedIn()
    }

    fun currentFlow(): MutableState<Flow<PagingData<Issue>>> {
        return currentFlow
    }

    fun applyPagingOption(newState: ListOptionsState) {
        if (_optionState.value != newState) {
            _optionState.value = newState
            loadIssues()
        }
    }
}