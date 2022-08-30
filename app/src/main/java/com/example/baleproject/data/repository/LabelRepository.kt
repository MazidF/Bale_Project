package com.example.baleproject.data.repository

import com.example.baleproject.data.LabelDataSource
import kotlinx.coroutines.CoroutineDispatcher

class LabelRepository(
    private val dataSource: LabelDataSource,
    private val dispatcher: CoroutineDispatcher,
)