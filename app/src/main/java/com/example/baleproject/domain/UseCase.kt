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
import com.example.baleproject.ui.model.LabelItem
import com.example.baleproject.utils.*
import com.example.baleproject.utils.helpers.ConnectionHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
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

    private val pagingConfig by lazy {
        PagingConfig(
            pageSize = PAGING_PAGE_SIZE,
            prefetchDistance = PAGING_FETCH_DISTANCE,
            initialLoadSize = PAGING_INITIAL_LOAD_SIZE,
            maxSize = PAGING_MAX_SIZE,
            enablePlaceholders = true,
        )
    }

    private var labels: HashMap<String, LabelItem> = hashMapOf()
    private var labelsJob: Job? = null

    init {
        initialSetup()
    }

    private fun initialSetup() {
        observeConnectionState()
    }

    private fun observeConnectionState() {
        connectionHelper.connectionState.observeForever {
/*            if (labelsHasBeenLoaded.not() and it.isConnected()) {
                loadLabels()
            }*/
        }
    }

    fun loadIssues(
        status: IssueStatus?,
        type: IssueType?,
        sortBy: SortBy?,
        sortType: SortType?,
        pagingConfig: PagingConfig = this.pagingConfig,
    ): Flow<PagingData<Issue>> {
        return ItemPagingSource.pager(
            config = pagingConfig,
            dataLoader = object : ItemPagingSource.PagingDataLoader<Issue> {
                override suspend fun loadData(
                    pageNumber: Int,
                    perPage: Int
                ): Result<List<Issue>> {
                    return issueRepository.getIssues(
                        offset = (pageNumber - 1) * perPage,
                        status = status,
                        type = type,
                        sortType = sortType,
                        sortBy = sortBy,
                    )
                }
            }
        ).flow.flowOn(dispatcher)
    }

    fun loadComments(
        issueId: String,
        pagingConfig: PagingConfig = this.pagingConfig,
    ): Flow<PagingData<Comment>> {
        return ItemPagingSource.pager(
            config = pagingConfig,
            dataLoader = object : ItemPagingSource.PagingDataLoader<Comment> {
                override suspend fun loadData(
                    pageNumber: Int,
                    perPage: Int
                ): Result<List<Comment>> {
                    return issueRepository.getCommentsOfIssue(
                        issueId = issueId,
                        offset = (pageNumber - 1) * perPage,
                    )
                }
            }
        ).flow.flowOn(dispatcher)
    }

    suspend fun loadLabels(ids: List<String>): Result<List<LabelItem>> {
        return if (updateLabels()) {
            val list = List(ids.size) {
                labels[ids[it]] ?: LabelItem.empty(ids[it])
            }
            Result.success(list)
        } else {
            Result.fail("Unable to get labels")
        }
    }

    private suspend fun updateLabels(): Boolean {
        val result = labelRepository.getLabels().map {
            this.associate {
                it.id to it.toLabelItem()
            }
        }
        val map = (result as? Result.Success)?.data() ?: return false
        labels.putAll(map)
        return true
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

    fun hasBeenLoggedIn(): Boolean {
        return userInfo != null
    }

    fun vote(issueId: String, vote: RawVote): Flow<Result<Unit>> {
        return safeApiCall {
            issueRepository.vote(
                header = userInfo!!.cookie,
                issueId = issueId,
                vote = vote,
            )
        }
    }

    fun createComment(issueId: String, comment: String): Flow<Result<Unit>> {
        return safeApiCall {
            issueRepository.createCommentForIssue(
                header = userInfo!!.cookie,
                issueId = issueId,
                text = comment
            )
        }
    }

    fun getIssue(issueId: String): Flow<Result<Issue>> {
        return safeApiCall {
            issueRepository.getIssue(issueId)
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