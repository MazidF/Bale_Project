package com.example.baleproject.data.model

enum class IssueStatus(
    val title: String,
    val description: String,
) {
    Pending(
        title = "Pending",
        description = ""
    ),
    InProgress(
        title = "In-Progress",
        description = ""
    ),
    Done(
        title = "Done",
        description = ""
    );
}