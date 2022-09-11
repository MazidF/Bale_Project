package com.example.baleproject.data.remote

import com.example.baleproject.data.IssueDataSource
import com.example.baleproject.data.model.*
import com.example.baleproject.data.remote.api.IssueApi
import com.example.baleproject.data.result.Result
import com.example.baleproject.utils.PAGING_PAGE_SIZE
import com.example.baleproject.utils.asResult

class RemoteIssueDataSource(
    private val issueApi: IssueApi,
) : IssueDataSource {

    override suspend fun getIssues(
        offset: Int,
        type: IssueType?,
        status: IssueStatus?,
        sortType: SortType?,
        sortBy: SortBy?
    ): Result<List<Issue>> {
        return issueApi.getIssues(offset, type?.name, status?.name, sortType?.name, sortBy?.name)
            .asResult()
    }

    override suspend fun createIssue(header: String, issue: RawIssue): Result<Unit> {
        return issueApi.createIssue(header, issue).asResult()
    }

    override suspend fun getIssue(issueId: String): Result<Issue> {
        var page = 1
        var result: Result<List<Issue>>
        var issues: List<Issue>

        do {
            result = getIssues(
                offset = (page - 1) * PAGING_PAGE_SIZE,
                sortType = SortType.ASC,
            ) as? Result.Success ?: break

            issues = result.data()
            issues.firstOrNull {
                it.id == issueId
            }?.let {
                return Result.success(it)
            }
            page++
        } while (issues.isNotEmpty())

        return Result.fail("Failed to get issue")
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
        return issueApi.createCommentForIssue(header, issueId, RawComment(text)).asResult()
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

    override suspend fun vote(header: String, issueId: String, vote: RawVote): Result<Unit> {
        val createResult = issueApi.vote(header, issueId, vote)
        if (createResult.code() == 400) {
            return issueApi.updateVote(header, issueId, vote).asResult()
        }
        return createResult.asResult()
    }
}