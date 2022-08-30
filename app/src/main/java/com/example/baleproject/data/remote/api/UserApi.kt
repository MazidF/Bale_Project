package com.example.baleproject.data.remote.api

import com.example.baleproject.data.model.LoginUser
import com.example.baleproject.data.model.RawUser
import com.example.baleproject.data.model.SignupUser
import com.example.baleproject.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String,
    ): Response<User>

    @PATCH("users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Body user: User,
    ): Response<User>

    @POST("users")
    suspend fun createUser(
        @Body rawUser: RawUser,
    ): Response<User>

    @POST("auth/signup")
    suspend fun signup(
        @Body user: SignupUser,
    ): Response<String>

    @POST("auth/login")
    suspend fun login(
        @Body user: LoginUser,
    ): Response<String>

}

