package com.example.baleproject.data

import com.example.baleproject.data.model.Label
import com.example.baleproject.data.result.Result

interface LabelDataSource {

    suspend fun getLabels(): Result<List<Label>>
}