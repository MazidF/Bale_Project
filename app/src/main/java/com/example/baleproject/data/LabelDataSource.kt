package com.example.baleproject.data

import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.LabelId
import com.example.baleproject.data.result.Result
import com.example.baleproject.ui.model.LabelItem

interface LabelDataSource {

    suspend fun getLabels(): Result<List<Label>>
    suspend fun crateLabels(header: String, labels: List<LabelItem>): List<LabelId>
}