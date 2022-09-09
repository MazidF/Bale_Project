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
        header: String,
        issue: RawIssue,
    ): Result<Unit>

    suspend fun getIssue(
        issueId: String,
    ): Result<Issue>

    suspend fun updateIssue(
        header: String,
        issueId: String,
        issue: RawIssue,
    ): Result<Unit>

    suspend fun getCommentsOfIssue(
        issueId: String,
        offset: Int,
    ): Result<List<Comment>>

    suspend fun createCommentForIssue(
        header: String,
        issueId: String,
        text: String,
    ): Result<Unit>

    suspend fun addLabelToIssue(
        header: String,
        issueId: String,
        labelIds: Array<String>,
    ): Result<Unit>

    suspend fun removeLabelOfIssue(
        header: String,
        issueId: String,
    ): Result<Unit>

    suspend fun vote(
        header: String,
        issueId: String,
        vote: RawVote,
    ): Result<Unit>

    // TODO: check Result<Unit> not to be fail all the time
}