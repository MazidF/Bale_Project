package com.example.baleproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = Issue.TABLE_NAME
)
data class Issue(
    @PrimaryKey val id: String,
    val vote: Vote,
    val title: String,
    val reviewed: Boolean,
    val commentsCount: Int,
    val description: String,
    val labelIds: List<String>,
) : Serializable {
    companion object {
        const val TABLE_NAME = "issue_table"
    }
}
