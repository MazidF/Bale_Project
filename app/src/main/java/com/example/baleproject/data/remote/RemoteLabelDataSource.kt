package com.example.baleproject.data.remote

import com.example.baleproject.data.LabelDataSource
import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.LabelId
import com.example.baleproject.data.remote.api.LabelApi
import com.example.baleproject.data.result.Result
import com.example.baleproject.ui.model.LabelItem
import com.example.baleproject.utils.asResult
import com.example.baleproject.utils.toRawLabel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class RemoteLabelDataSource(
    private val labelApi: LabelApi,
    dispatcher: CoroutineDispatcher,
) : LabelDataSource {
    private val scope by lazy {
        CoroutineScope(dispatcher)
    }

    override suspend fun getLabels(): Result<List<Label>> {
        return labelApi.getLabels().asResult()
    }

    override suspend fun crateLabels(header: String, labels: List<LabelItem>): List<LabelId> {
        val list = labels.map(LabelItem::toRawLabel)
        val requests = list.map {
            scope.async {
                labelApi.createLabel(header, it).body()
            }
        }
        val ids = requests.awaitAll()
        return ids.filterNotNull()
    }
}