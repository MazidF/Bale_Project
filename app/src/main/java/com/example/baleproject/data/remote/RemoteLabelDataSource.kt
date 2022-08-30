package com.example.baleproject.data.remote

import com.example.baleproject.data.LabelDataSource
import com.example.baleproject.data.model.Label
import com.example.baleproject.data.remote.api.LabelApi
import com.example.baleproject.data.result.Result
import com.example.baleproject.utils.asResult

class RemoteLabelDataSource(
    private val labelApi: LabelApi,
) : LabelDataSource {

    override suspend fun getLabels(): Result<List<Label>> {
        return labelApi.getLabels().asResult()
    }
}