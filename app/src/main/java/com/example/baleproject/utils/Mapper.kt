package com.example.baleproject.utils

import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.Label
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