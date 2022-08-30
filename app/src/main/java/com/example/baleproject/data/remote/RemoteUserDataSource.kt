package com.example.baleproject.data.remote

import com.example.baleproject.data.UserDataSource
import com.example.baleproject.data.model.LoginUser
import com.example.baleproject.data.model.RawUser
import com.example.baleproject.data.model.SignupUser
import com.example.baleproject.data.model.User
import com.example.baleproject.data.remote.api.UserApi
import com.example.baleproject.data.result.Result
import com.example.baleproject.utils.asResult

class RemoteUserDataSource(
    private val userApi: UserApi,
) : UserDataSource {

    override suspend fun getUser(userId: String): Result<User> {
        return userApi.getUser(userId).asResult()
    }

    override suspend fun updateUser(userId: String, user: User): Result<User> {
        return userApi.updateUser(userId, user).asResult()
    }

    override suspend fun createUser(rawUser: RawUser): Result<User> {
        return userApi.createUser(rawUser).asResult()
    }

    override suspend fun signup(user: SignupUser): Result<String> {
        return userApi.signup(user).asResult()
    }

    override suspend fun login(user: LoginUser): Result<String> {
        return userApi.login(user).asResult()
    }
}