package com.example.baleproject.data.model

data class Issue(
    val id: String,
    val title: String,
    val description: String,
    val vote: Vote,
    val reviewed: Boolean,
    val labelIds: List<String>,
)
