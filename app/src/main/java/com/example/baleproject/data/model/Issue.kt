package com.example.baleproject.data.model

data class Issue(
    val id: String,
    val vote: Vote,
    val title: String,
    val reviewed: Boolean,
    val commentCounts: Int,
    val description: String,
    val labelIds: List<String>,
)
