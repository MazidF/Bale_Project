package com.example.baleproject.domain

import android.content.Context
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.baleproject.data.model.*
import com.example.baleproject.data.repository.IssueRepository
import com.example.baleproject.data.repository.LabelRepository
import com.example.baleproject.data.repository.UserRepository
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.paging.ItemPagingSource
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.utils.getEmailAndPassword
import com.example.baleproject.utils.helpers.ConnectionHelper
import com.example.baleproject.utils.toIssueItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class UseCase(
    private val context: Context,
    private val userRepository: UserRepository,
    private val labelRepository: LabelRepository,
    private val issueRepository: IssueRepository,
    private val dispatcher: CoroutineDispatcher,
    private val connectionHelper: ConnectionHelper,
) {
    private val scope by lazy {
        CoroutineScope(dispatcher + SupervisorJob())
    }

    private var userInfo: UserInfo? = null

    private var labels: List<Label> = emptyList()
    private var labelsHasBeenLoaded = false
    private var labelsJob: Job? = null

    init {
        initialSetup()
    }

    private fun initialSetup() {
        observeConnectionState()
    }

    private fun observeConnectionState() {
        connectionHelper.connectionState.observeForever {
            if (labelsHasBeenLoaded.not() and it.isConnected()) {
                loadLabels()
            }
        }
    }

    private fun loadLabels() = scope.launch {
        labelsJob?.cancelAndJoin()
        labelsJob = scope.launch {
            repeat(3) { // try 3 times to retrieve labels
                val flow = safeApiCall(false) {
                    labelRepository.getLabels()
                }
                val result = flow.firstOrNull {
                    it is Result.Success
                } as? Result.Success
                result?.let {
                    labels = it.data()
                    labelsHasBeenLoaded = true
                    return@launch
                }
                delay(2_000)
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
        return labels.filter {
            it.id in labelIds
        }
    }

    fun signup(name: String, email: String, password: String): Flow<Result<Unit>> {
        val user = SignupUser(
            name = name,
            email = email,
            password = password,
        )
        return safeApiCall {
            userRepository.signup(user)
        }
    }

    fun login(email: String, password: String): Flow<Result<UserInfo>> {
        val user = LoginUser(
            email = email,
            password = password,
        )
        return safeApiCall {
            userRepository.login(user).also {
                saveUserInfo(it)
            }
        }
    }

    suspend fun autoLogin(): Result<Unit> {
        val (email, password) = getEmailAndPassword(context) ?: return Result.success(Unit)
        val user = LoginUser(
            email = email,
            password = password,
        )
        return userRepository.login(user).map {}
    }

    private fun saveUserInfo(result: Result<UserInfo>) {
        if (result is Result.Success) {
            saveUserInfo(result.data())
        }
    }

    private fun saveUserInfo(userInfo: UserInfo) {
        this.userInfo = userInfo
    }

    fun getUserName(): String {
        return userInfo!!.name
    }

    suspend fun createIssue(
        title: String,
        description: String,
        type: IssueType,
        labelIds: List<String>,
    ): Flow<Result<Unit>> {
        val rawIssue = RawIssue(
            title = title,
            description = description,
            type = type,
            labelIds = labelIds,
        )
        return safeApiCall {
            issueRepository.createIssue(userInfo!!.cookie, rawIssue)
        }
    }

    private fun <T> safeApiCall(
        emitLoading: Boolean = true,
        block: suspend () -> Result<T>,
    ): Flow<Result<T>> {
        return flow {
            emit(block())
        }.onStart {
            if (emitLoading) {
                emit(Result.loading())
            }
        }.catch { cause ->
            emit(Result.fail(cause))
        }.flowOn(dispatcher)
    }
}