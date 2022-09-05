package com.example.baleproject.domain.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

@OptIn(ExperimentalPagingApi::class)
class ItemRemoteMediator<T : Any> : RemoteMediator<Int, T>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, T>): MediatorResult {
        return MediatorResult.Success(true)
    }
}