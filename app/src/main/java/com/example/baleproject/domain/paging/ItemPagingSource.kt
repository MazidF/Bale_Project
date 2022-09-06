package com.example.baleproject.domain.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.baleproject.data.result.Result
import com.example.baleproject.utils.PAGING_FIRST_PAGE_INDEX

class ItemPagingSource<T : Any>(
    private val dataLoader: PagingDataLoader<T>,
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: PAGING_FIRST_PAGE_INDEX
        val data = dataLoader.loadData(pageNumber = page, perPage = params.loadSize)
        return if (data is Result.Success) {
            val items = data.data()
            LoadResult.Page(
                data = items,
                prevKey = if (page == PAGING_FIRST_PAGE_INDEX) null else page.minus(1),
                nextKey = if (items.size < params.loadSize) null else page.plus(1),
            )
        } else {
            LoadResult.Error((data as Result.Fail).error())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }
    }

    interface PagingDataLoader<T : Any> {
        suspend fun loadData(pageNumber: Int, perPage: Int): Result<List<T>>
    }


    companion object {
        fun <T : Any> pager(config: PagingConfig, dataLoader: PagingDataLoader<T>): Pager<Int, T> {
            return Pager(config = config) {
                ItemPagingSource(dataLoader = dataLoader)
            }
        }
    }
}