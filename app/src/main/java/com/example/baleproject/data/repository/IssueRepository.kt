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
        header: String,
        issue: RawIssue,
    ): Result<Unit> {
        return dataSource.createIssue(header, issue)
    }

    suspend fun getIssue(
        issueId: String,
    ): Result<Issue> {
        return dataSource.getIssue(issueId)
    }

    suspend fun updateIssue(
        header: String,
        issueId: String,
        issue: RawIssue,
    ): Result<Unit> {
        return dataSource.updateIssue(header, issueId, issue)
    }

    suspend fun getCommentsOfIssue(
        issueId: String,
        offset: Int,
    ): Result<List<Comment>> {
        return dataSource.getCommentsOfIssue(issueId, offset)
    }

    suspend fun createCommentForIssue(
        header: String,
        issueId: String,
        text: String,
    ): Result<Unit> {
        return dataSource.createCommentForIssue(header, issueId, text)
    }

    suspend fun addLabelToIssue(
        header: String,
        issueId: String,
        labelIds: Array<String>,
    ): Result<Unit> {
        return dataSource.addLabelToIssue(header, issueId, labelIds)
    }

    suspend fun removeLabelOfIssue(
        header: String,
        issueId: String,
    ): Result<Unit> {
        return dataSource.removeLabelOfIssue(header, issueId)
    }

    suspend fun vote(
        header: String,
        issueId: String,
        vote: RawVote,
    ): Result<Unit> {
        return dataSource.vote(header, issueId, vote)
    }
}
