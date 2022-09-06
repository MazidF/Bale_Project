package com.example.baleproject.ui.screens.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.IssueStatus
import com.example.baleproject.domain.UseCase
import com.example.baleproject.utils.PAGING_FETCH_DISTANCE
import com.example.baleproject.utils.PAGING_INITIAL_LOAD_SIZE
import com.example.baleproject.utils.PAGING_MAX_SIZE
import com.example.baleproject.utils.PAGING_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    private val pagingConfig by lazy {
        PagingConfig(
            pageSize = PAGING_PAGE_SIZE,
            prefetchDistance = PAGING_FETCH_DISTANCE,
            initialLoadSize = PAGING_INITIAL_LOAD_SIZE,
            maxSize = PAGING_MAX_SIZE,
            enablePlaceholders = true,
        )
    }

    private var currentFlow: MutableState<Flow<PagingData<Issue>>> = mutableStateOf(flow { })


    init {
        loadIssues(status = IssueStatus.None)
    }

    fun loadIssues(status: IssueStatus) {
        currentFlow.value = useCase.loadIssues(
            status = status, pagingConfig = pagingConfig
        ).cachedIn(viewModelScope)
    }

    fun hasBeenLogin(): Boolean {
        return useCase.hasBeenLogin()
    }

    fun currentFlow(): MutableState<Flow<PagingData<Issue>>> {
        return currentFlow
    }
}