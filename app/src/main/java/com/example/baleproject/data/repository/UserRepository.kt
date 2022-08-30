package com.example.baleproject.data.repository

import com.example.baleproject.data.UserDataSource
import com.example.baleproject.data.model.LoginUser
import com.example.baleproject.data.model.RawUser
import com.example.baleproject.data.model.SignupUser
import com.example.baleproject.data.model.User
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
        userId: String,
        user: User,
    ): Result<User> {
        return dataSource.updateUser(userId, user)
    }

    suspend fun createUser(
        rawUser: RawUser,
    ): Result<User> {
        return dataSource.createUser(rawUser)
    }

    suspend fun signup(
        user: SignupUser,
    ): Result<String> {
        return dataSource.signup(user)
    }

    suspend fun login(
        user: LoginUser,
    ): Result<String> {
        return dataSource.login(user)
    }
}