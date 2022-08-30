package com.example.baleproject.data

import com.example.baleproject.data.model.*
import com.example.baleproject.data.result.Result

interface IssueDataSource {

    suspend fun getIssues(
        offset: Int,
        type: IssueType,
        status: IssueStatus,
        sortType: SortType,
    ): Result<List<Issue>>

    suspend fun createIssue(
        issue: RawIssue,
    ): Result<String>

    suspend fun getIssue(
        issueId: String,
    ): Result<Issue>

    suspend fun updateIssue(
        issueId: String,
        issue: RawIssue,
    ): Result<Unit>

    suspend fun getCommentsOfIssue(
        issueId: String,
        offset: Int,
    ): Result<List<Comment>>

    suspend fun createCommentForIssue(
        issueId: String,
        text: String,
    ): Result<String>

    suspend fun addLabelToIssue(
        issueId: String,
        labelIds: Array<String>,
    ): Result<Unit>

    suspend fun removeLabelOfIssue(
        issueId: String,
    ): Result<Unit>

    // TODO: check Result<Unit> not to be fail all the time
}