package com.example.baleproject.utils

import androidx.compose.ui.graphics.Color
import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.User
import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.ui.model.LabelItem

fun Issue.toIssueItem(labels: List<LabelItem>): IssueItem {
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

fun Label.toLabelItem(): LabelItem {
    return LabelItem(
        id = id,
        name = name,
        color = Color(color),
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
