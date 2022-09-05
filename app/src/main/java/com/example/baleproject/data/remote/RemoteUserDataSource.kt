package com.example.baleproject.data.remote

import com.example.baleproject.data.UserDataSource
import com.example.baleproject.data.model.*
import com.example.baleproject.data.remote.api.UserApi
import com.example.baleproject.data.result.Result
import com.example.baleproject.utils.SET_COOKIE
import com.example.baleproject.utils.asResult
import com.example.baleproject.utils.logger
import retrofit2.Response

class RemoteUserDataSource(
    private val userApi: UserApi,
) : UserDataSource {

    override suspend fun getUser(userId: String): Result<User> {
        return userApi.getUser(userId).asResult()
    }

    override suspend fun updateUser(header: String, userId: String, user: User): Result<User> {
        return userApi.updateUser(header, userId, user).asResult()
    }

    override suspend fun createUser(rawUser: RawUser): Result<User> {
        return userApi.createUser(rawUser).asResult()
    }

    override suspend fun signup(user: SignupUser): Result<Unit> {
        return userApi.signup(user).asResult()
    }

    override suspend fun login(user: LoginUser): Result<UserInfo> {
        val response = userApi.login(user)
        return getAccessToken(response)
    }

    private fun getAccessToken(response: Response<UserInfo>): Result<UserInfo> {
        val headers = response.headers()
        logger(headers.names().toString())
        val accessToken = headers.get(SET_COOKIE)
            ?: return Result.fail("Unable to resolve access_token!!")
        return response.asResult {
            val index = accessToken.indexOf("; Path=/;")
            this.copy(
                accessToken = accessToken.substring(0, index).removePrefix("access_token=")
            )
            /*UserInfo(
                id = id,
                name = name,
                email = email,
                verified = verified,
                accessToken = accessToken,
            )*/
        }
    }
}