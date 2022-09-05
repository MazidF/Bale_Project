package com.example.baleproject.utils

import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.User
import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.ui.model.IssueItem

fun Issue.toIssueItem(labels: List<Label>): IssueItem {
    return IssueItem(
        id = id,
        vote = vote,
        title = title,
        labels = labels,
        reviewed = reviewed,
        description = description,
        commentCounts = commentCounts,
    )
}

fun UserInfo.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        verified = verified,
    )
}
