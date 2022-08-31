package com.example.baleproject.domain

import androidx.compose.ui.graphics.Color
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.baleproject.data.model.*
import com.example.baleproject.data.repository.IssueRepository
import com.example.baleproject.data.repository.LabelRepository
import com.example.baleproject.data.repository.UserRepository
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.paging.ItemPagingSource
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.utils.toIssueItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UseCase(
    private val userRepository: UserRepository,
    private val labelRepository: LabelRepository,
    private val issueRepository: IssueRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    private val scope by lazy {
        CoroutineScope(dispatcher + SupervisorJob())
    }

    private lateinit var user: User
    private var labels: List<Label> = emptyList()

    init {
        initialSetup()
    }

    private fun initialSetup() {
//        loadLabels()
    }

    private fun loadLabels() = scope.launch {
        repeat(3) { // try 3 times to retrieve labels
            val result = labelRepository.getLabels()
            if (result is Result.Success) {
                labels = result.data()
                return@launch
            }
        }
    }

    fun loadIssues(
        status: IssueStatus,
        type: IssueType = IssueType.None,
        sortType: SortType = SortType.ASC,
        pagingConfig: PagingConfig,
    ): Flow<PagingData<IssueItem>> {

        return ItemPagingSource.pager(
            config = pagingConfig,
            dataLoader = object : ItemPagingSource.PagingDataLoader<IssueItem> {
                override suspend fun loadData(
                    pageNumber: Int,
                    perPage: Int
                ): Result<List<IssueItem>> {
                    return issueRepository.getIssues(
                        offset = (pageNumber - 1) * perPage,
                        status = status,
                        type = type,
                        sortType = sortType,
                    ).map {
                        map { issue ->
                            issue.toIssueItem(issue.getIssueLabels())
                        }
                    }
                }
            }
        ).flow
    }

    private fun Issue.getIssueLabels(): List<Label> {
        /*labels.filter {
            it.id in labelIds
        }*/
        return listOf(
            Label(
                id = "-1",
                name = "Programming",
                color = Color.Red,
            ),
            Label(
                id = "-1",
                name = "Game",
                color = Color.Blue,
            ),
        )
    }

/*    fun userRepository(block: UserRepository.() -> Unit) {
        userRepository.block()
    }

    fun labelRepository(block: LabelRepository.() -> Unit) {
        labelRepository.block()
    }

    fun issueRepository(block: IssueRepository.() -> Unit) {
        issueRepository.block()
    }

    suspend fun userRepository(block: suspend UserRepository.() -> Unit) {
        userRepository.block()
    }

    suspend fun labelRepository(block: suspend LabelRepository.() -> Unit) {
        labelRepository.block()
    }

    suspend fun issueRepository(block: suspend IssueRepository.() -> Unit) {
        issueRepository.block()
    }*/

    fun <T> safeApiCall(
        block: suspend () -> Result<T>,
    ): Flow<Result<T>> {
        return flow {
            emit(block())
        }.onStart {
            emit(Result.loading())
        }.catch { cause ->
            emit(Result.fail(cause))
        }.flowOn(dispatcher)
    }
}