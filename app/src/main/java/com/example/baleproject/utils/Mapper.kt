package com.example.baleproject.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.baleproject.data.model.*
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
        commentsCount = commentsCount,
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

fun LabelItem.toRawLabel(): RawLabel {
    return RawLabel(name, color.toArgb())
}
