package com.example.baleproject.di

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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}