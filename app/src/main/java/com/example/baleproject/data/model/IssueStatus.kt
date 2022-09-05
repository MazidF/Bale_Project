package com.example.baleproject.data.model

enum class IssueStatus(
    val query: String?,
) {
    Pending("Pending"),
    InProgress("InProgress"),
    Done("Done"),
    None(null);
}