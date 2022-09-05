package com.example.baleproject.di

import android.content.Context
import com.example.baleproject.data.IssueDataSource
import com.example.baleproject.data.LabelDataSource
import com.example.baleproject.data.UserDataSource
import com.example.baleproject.data.remote.RemoteIssueDataSource
import com.example.baleproject.data.remote.RemoteLabelDataSource
import com.example.baleproject.data.remote.RemoteUserDataSource
import com.example.baleproject.data.remote.api.IssueApi
import com.example.baleproject.data.remote.api.LabelApi
import com.example.baleproject.data.remote.api.UserApi
import com.example.baleproject.data.repository.IssueRepository
import com.example.baleproject.data.repository.LabelRepository
import com.example.baleproject.data.repository.UserRepository
import com.example.baleproject.di.qualifiers.IO
import com.example.baleproject.domain.UseCase
import com.example.baleproject.utils.helpers.ConnectionHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUserDataSource(
        userApi: UserApi,
    ): UserDataSource {
        return RemoteUserDataSource(
            userApi = userApi,
        )
    }

    @Provides
    @Singleton
    fun provideLabelDataSource(
        labelApi: LabelApi,
    ): LabelDataSource {
        return RemoteLabelDataSource(
            labelApi = labelApi,
        )
    }

    @Provides
    @Singleton
    fun provideIssueDataSource(
        issueApi: IssueApi,
    ): IssueDataSource {
        return RemoteIssueDataSource(
            issueApi = issueApi,
        )
    }

    @Provides
    @Singleton
    fun provideIssueRepository(
        dataSource: IssueDataSource,
        @IO dispatcher: CoroutineDispatcher,
    ): IssueRepository {
        return IssueRepository(
            dataSource = dataSource,
            dispatcher = dispatcher,
        )
    }

/*
    object : IssueDataSource {
        override suspend fun getIssues(
            offset: Int,
            type: IssueType,
            status: IssueStatus,
            sortType: SortType
        ): Result<List<Issue>> {
            delay(2000)
            val items = List(20) {
                val itemId = it + offset
                Issue(
                    id = itemId.toString(),
                    title = "Title of issues number $itemId",
                    description = "Description of issues number $itemId whit some other words.",
                    vote = if (Random.nextBoolean()) {
                        Vote.Up(Random.nextInt(1, 43), 0)
                    } else {
                        Vote.Down(0, Random.nextInt(1, 43))
                    },
                    reviewed = false,
                    labelIds = List(Random.nextInt(0, 3)) {
                        Random.nextInt().toString()
                    },
                    commentCounts = Random.nextInt(1, 43),
                )
            }
            return Result.success(items)
        }

        override suspend fun createIssue(issue: RawIssue): Result<String> {
            TODO("Not yet implemented")
        }

        override suspend fun getIssue(issueId: String): Result<Issue> {
            TODO("Not yet implemented")
        }

        override suspend fun updateIssue(issueId: String, issue: RawIssue): Result<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun getCommentsOfIssue(
            issueId: String,
            offset: Int
        ): Result<List<Comment>> {
            TODO("Not yet implemented")
        }

        override suspend fun createCommentForIssue(
            issueId: String,
            text: String
        ): Result<String> {
            TODO("Not yet implemented")
        }

        override suspend fun addLabelToIssue(
            issueId: String,
            labelIds: Array<String>
        ): Result<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun removeLabelOfIssue(issueId: String): Result<Unit> {
            TODO("Not yet implemented")
        }

    }
    */

    @Provides
    @Singleton
    fun provideUserRepository(
        dataSource: UserDataSource,
        @IO dispatcher: CoroutineDispatcher,
    ): UserRepository {
        return UserRepository(
            dataSource = dataSource,
            dispatcher = dispatcher,
        )
    }

    @Provides
    @Singleton
    fun provideLabelRepository(
        dataSource: LabelDataSource,
        @IO dispatcher: CoroutineDispatcher,
    ): LabelRepository {
        return LabelRepository(
            dataSource = dataSource,
            dispatcher = dispatcher,
        )
    }

    @Provides
    @Singleton
    fun provideUseCase(
        @ApplicationContext context: Context,
        userRepository: UserRepository,
        issueRepository: IssueRepository,
        labelRepository: LabelRepository,
        connectionHelper: ConnectionHelper,
        @IO dispatcher: CoroutineDispatcher,
    ): UseCase {
        return UseCase(
            context = context,
            userRepository = userRepository,
            labelRepository = labelRepository,
            issueRepository = issueRepository,
            dispatcher = dispatcher,
            connectionHelper = connectionHelper,
        )
    }
}