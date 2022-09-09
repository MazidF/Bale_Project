package com.example.baleproject.ui.model

import com.example.baleproject.data.model.Vote
import java.io.Serializable

data class IssueItem(
    val id: String,
    val vote: Vote,
    val title: String,
    val reviewed: Boolean,
    val commentsCount: Int,
    val description: String,
    val labels: List<LabelItem>,
) : Serializable