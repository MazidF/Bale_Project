package com.example.baleproject.data

import com.example.baleproject.data.model.*
import com.example.baleproject.data.result.Result

interface UserDataSource {

    suspend fun getUser(
        userId: String,
    ): Result<User>

    suspend fun updateUser(
        header: String,
        userId: String,
        user: User,
    ): Result<User>

    suspend fun createUser(
        rawUser: RawUser,
    ): Result<User>

    suspend fun signup(
        user: SignupUser,
    ): Result<Unit>

    suspend fun login(
        user: LoginUser,
    ): Result<UserInfo>
}