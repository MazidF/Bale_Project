package com.example.baleproject.data.repository

import com.example.baleproject.data.IssueDataSource
import kotlinx.coroutines.CoroutineDispatcher

class IssueRepository(
    private val dataSource: IssueDataSource,
    private val dispatcher: CoroutineDispatcher,
)
