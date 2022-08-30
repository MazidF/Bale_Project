package com.example.baleproject.data.model

data class RawIssue(
    val title: String,
    val description: String,
    val type: IssueType,
    val labelIds: List<String>,
    // TODO: add status for admin
)