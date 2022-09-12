package com.example.baleproject.data.repository

import com.example.baleproject.data.UserDataSource
import com.example.baleproject.data.model.*
import com.example.baleproject.data.result.Result
import kotlinx.coroutines.CoroutineDispatcher

class UserRepository(
    private val dataSource: UserDataSource,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getUser(
        userId: String,
    ): Result<User> {
        return dataSource.getUser(userId)
    }

    suspend fun updateUser(
        header: String,
        userId: String,
        user: RawUser,
    ): Result<User> {
        return dataSource.updateUser(header, userId, user)
    }

    suspend fun createUser(
        rawUser: RawUser,
    ): Result<User> {
        return dataSource.createUser(rawUser)
    }

    suspend fun signup(
        user: SignupUser,
    ): Result<Unit> {
        return dataSource.signup(user)
    }

    suspend fun login(
        user: LoginUser,
    ): Result<UserInfo> {
        return dataSource.login(user)
    }
}