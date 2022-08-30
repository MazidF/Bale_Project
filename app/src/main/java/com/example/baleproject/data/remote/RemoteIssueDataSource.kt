package com.example.baleproject.data.remote

import com.example.baleproject.data.model.*
import com.example.baleproject.data.IssueDataSource
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
        return issueApi.getIssues(offset, type, status, sortType).asResult()
    }

    override suspend fun createIssue(issue: RawIssue): Result<String> {
        return issueApi.createIssue(issue).asResult()
    }

    override suspend fun getIssue(issueId: String): Result<Issue> {
        return issueApi.getIssue(issueId).asResult()
    }

    override suspend fun updateIssue(issueId: String, issue: RawIssue): Result<Unit> {
        return issueApi.updateIssue(issueId, issue).asResult()
    }

    override suspend fun getCommentsOfIssue(issueId: String, offset: Int): Result<List<Comment>> {
        return issueApi.getCommentsOfIssue(issueId, offset).asResult()
    }

    override suspend fun createCommentForIssue(issueId: String, text: String): Result<String> {
        return issueApi.createCommentForIssue(issueId, text).asResult()
    }

    override suspend fun addLabelToIssue(issueId: String, labelIds: Array<String>): Result<Unit> {
        return issueApi.addLabelToIssue(issueId, labelIds).asResult()
    }

    override suspend fun removeLabelOfIssue(issueId: String): Result<Unit> {
        return issueApi.removeLabelOfIssue(issueId).asResult()
    }
}