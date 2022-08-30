package com.example.baleproject.data.repository

import com.example.baleproject.data.IssueDataSource
import com.example.baleproject.data.model.*
import com.example.baleproject.data.result.Result
import kotlinx.coroutines.CoroutineDispatcher

class IssueRepository(
    private val dataSource: IssueDataSource,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getIssues(
        offset: Int,
        type: IssueType,
        status: IssueStatus,
        sortType: SortType,
    ): Result<List<Issue>> {
        return dataSource.getIssues(offset, type, status, sortType)
    }

    suspend fun createIssue(
        issue: RawIssue,
    ): Result<String> {
        return dataSource.createIssue(issue)
    }

    suspend fun getIssue(
        issueId: String,
    ): Result<Issue> {
        return dataSource.getIssue(issueId)
    }

    suspend fun updateIssue(
        issueId: String,
        issue: RawIssue,
    ): Result<Unit> {
        return dataSource.updateIssue(issueId, issue)
    }

    suspend fun getCommentsOfIssue(
        issueId: String,
        offset: Int,
    ): Result<List<Comment>> {
        return dataSource.getCommentsOfIssue(issueId, offset)
    }

    suspend fun createCommentForIssue(
        issueId: String,
        text: String,
    ): Result<String> {
        return dataSource.createCommentForIssue(issueId, text)
    }

    suspend fun addLabelToIssue(
        issueId: String,
        labelIds: Array<String>,
    ): Result<Unit> {
        return dataSource.addLabelToIssue(issueId, labelIds)
    }

    suspend fun removeLabelOfIssue(
        issueId: String,
    ): Result<Unit> {
        return dataSource.removeLabelOfIssue(issueId)
    }
}
