package com.example.baleproject.data

import com.example.baleproject.data.model.LoginUser
import com.example.baleproject.data.model.RawUser
import com.example.baleproject.data.model.SignupUser
import com.example.baleproject.data.model.User
import com.example.baleproject.data.result.Result

interface UserDataSource {

    suspend fun getUser(
        userId: String,
    ): Result<User>

    suspend fun updateUser(
        userId: String,
        user: User,
    ): Result<User>

    suspend fun createUser(
        rawUser: RawUser,
    ): Result<User>

    suspend fun signup(
        user: SignupUser,
    ): Result<String>

    suspend fun login(
        user: LoginUser,
    ): Result<String>
}