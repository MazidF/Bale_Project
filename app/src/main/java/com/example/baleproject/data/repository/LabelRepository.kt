package com.example.baleproject.data.repository

import com.example.baleproject.data.LabelDataSource
import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.LabelId
import com.example.baleproject.data.result.Result
import com.example.baleproject.ui.model.LabelItem
import kotlinx.coroutines.CoroutineDispatcher

class LabelRepository(
    private val dataSource: LabelDataSource,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getLabels(): Result<List<Label>> {
        return dataSource.getLabels()
    }

    suspend fun createLabels(header: String, labels: List<LabelItem>): List<LabelId> {
        return dataSource.crateLabels(header, labels)
    }
}