package com.example.baleproject.data.remote.api

import com.example.baleproject.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface IssueApi {

    @GET("issues")
    suspend fun getIssues(
        @Query("offset") offset: Int,
        @Query("type") type: IssueType,
        @Query("status") status: IssueStatus,
        @Query("sortType") sortType: SortType,
        @Query("sortBy") sortBy: String = "Date",
    ): Response<List<Issue>>

    @POST("issues")
    suspend fun createIssue(
        @Body issue: RawIssue,
    ): Response<String>

    @GET("issues/{issueId}")
    suspend fun getIssue(
        @Path("issueId") issueId: String,
    ): Response<Issue>

    @PATCH("issues/{issueId}")
    suspend fun updateIssue(
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
        @Path("issueId") issueId: String,
        @Body text: String,
    ): Response<String>

    @POST("issues/{issueId}/labels")
    suspend fun addLabelToIssue(
        @Path("issueId") issueId: String,
        @Body labelIds: Array<String>,
    ): Response<Unit>

    @DELETE("issues/{issueId}/labels")
    suspend fun removeLabelOfIssue(
        @Path("issueId") issueId: String,
    ): Response<Unit>

    // TODO: add api for updating votes
}
