package com.example.baleproject.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.IssueStatus
import com.example.baleproject.domain.UseCase
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    private val pagingConfig by lazy {
        PagingConfig(
            pageSize = PAGING_PAGE_SIZE,
            prefetchDistance = PAGING_FETCH_DISTANCE,
            enablePlaceholders = true,
            initialLoadSize = PAGING_INITIAL_LOAD_SIZE,
            maxSize = PAGING_MAX_SIZE,
        )
    }

    fun loadIssues(
        status: IssueStatus,
    ): Flow<PagingData<IssueItem>> {
        return useCase.loadIssues(
            status = status, pagingConfig = pagingConfig
        ).cachedIn(viewModelScope)
    }
}