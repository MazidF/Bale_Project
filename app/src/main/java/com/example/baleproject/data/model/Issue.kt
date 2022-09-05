package com.example.baleproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = Issue.TABLE_NAME
)
data class Issue(
    @PrimaryKey val id: String,
    val vote: Vote,
    val title: String,
    val reviewed: Boolean,
    val commentCounts: Int,
    val description: String,
    val labelIds: List<String>,
) {
    companion object {
        const val TABLE_NAME = "issue_table"
    }
}
