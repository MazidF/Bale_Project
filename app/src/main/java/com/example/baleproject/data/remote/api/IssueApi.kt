package com.example.baleproject.data.remote.api

import com.example.baleproject.data.model.*
import com.example.baleproject.utils.ACCESS_TOKEN_KEY
import retrofit2.Response
import retrofit2.http.*

interface IssueApi {

    @GET("issues")
    suspend fun getIssues(
        @Query("offset") offset: Int,
        @Query("type") type: String? = null,
        @Query("status") status: String? = null,
        @Query("sortType") sortType: String? = null,
        @Query("sortBy") sortBy: String? = null,
    ): Response<List<Issue>>

    @POST("issues")
    suspend fun createIssue(
        @Header(ACCESS_TOKEN_KEY) header: String,
        @Body issue: RawIssue,
    ): Response<Unit>

    @PATCH("issues/{issueId}")
    suspend fun updateIssue(
        @Header(ACCESS_TOKEN_KEY) header: String,
        @Path("issueId") issueId: String,
        issue: RawIssue,
    ): Response<Unit>

    @GET("issues/{issueId}/comments")
    suspend fun getCommentsOfIssue(
        @Path("issueId") issueId: String,
        @Query("offset") offset: Int,
    ): Response<List<Comment>>

    @POST("issues/{issueId}/comments")
    suspend fun createCommentForIssue(
        @Header(ACCESS_TOKEN_KEY) header: String,
        @Path("issueId") issueId: String,
        @Body comment: RawComment,
    ): Response<Unit>

    @POST("issues/{issueId}/labels")
    suspend fun addLabelToIssue(
        @Header(ACCESS_TOKEN_KEY) header: String,
        @Path("issueId") issueId: String,
        @Body labelIds: Array<String>,
    ): Response<Unit>

    @DELETE("issues/{issueId}/labels")
    suspend fun removeLabelOfIssue(
        @Header(ACCESS_TOKEN_KEY) header: String,
        @Path("issueId") issueId: String,
    ): Response<Unit>

    @POST("issues/{issueId}/votes")
    suspend fun vote(
        @Header(ACCESS_TOKEN_KEY) header: String,
        @Path("issueId") issueId: String,
        @Body vote: RawVote,
    ): Response<Unit>

    @PATCH("issues/{issueId}/votes")
    suspend fun updateVote(
        @Header(ACCESS_TOKEN_KEY) header: String,
        @Path("issueId") issueId: String,
        @Body vote: RawVote,
    ): Response<Unit>
}
