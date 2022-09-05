package com.example.baleproject.data.remote

import com.example.baleproject.data.IssueDataSource
import com.example.baleproject.data.model.*
import com.example.baleproject.data.remote.api.IssueApi
import com.example.baleproject.data.result.Result
import com.example.baleproject.utils.asResult

class RemoteIssueDataSource(
    private val issueApi: IssueApi,
) : IssueDataSource {

    override suspend fun getIssues(
        offset: Int,
        type: IssueType,
        status: IssueStatus,
        sortType: SortType
    ): Result<List<Issue>> {
        return issueApi.getIssues(offset, type.query, status.query, sortType.query).asResult()
    }

    override suspend fun createIssue(header: String, issue: RawIssue): Result<Unit> {
        return issueApi.createIssue(header, issue).asResult()
    }

    override suspend fun getIssue(issueId: String): Result<Issue> {
        return issueApi.getIssue(issueId).asResult()
    }

    override suspend fun updateIssue(
        header: String,
        issueId: String,
        issue: RawIssue
    ): Result<Unit> {
        return issueApi.updateIssue(header, issueId, issue).asResult()
    }

    override suspend fun getCommentsOfIssue(issueId: String, offset: Int): Result<List<Comment>> {
        return issueApi.getCommentsOfIssue(issueId, offset).asResult()
    }

    override suspend fun createCommentForIssue(
        header: String,
        issueId: String,
        text: String
    ): Result<Unit> {
        return issueApi.createCommentForIssue(header, issueId, text).asResult()
    }

    override suspend fun addLabelToIssue(
        header: String,
        issueId: String,
        labelIds: Array<String>
    ): Result<Unit> {
        return issueApi.addLabelToIssue(header, issueId, labelIds).asResult()
    }

    override suspend fun removeLabelOfIssue(header: String, issueId: String): Result<Unit> {
        return issueApi.removeLabelOfIssue(header, issueId).asResult()
    }
}