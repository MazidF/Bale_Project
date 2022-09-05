package com.example.baleproject.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.baleproject.data.model.Issue
import kotlinx.coroutines.flow.Flow

@Dao
interface IssueDao : IDao<Issue> {

    @Query("select * from issue_table where id = :id")
    suspend fun get(id: Long): Issue

    @Query("select * from issue_table")
    fun getAll(): Flow<List<Issue>>

    @Query("select * from issue_table limit :limit offset :offset")
    fun getAll(limit: Int, offset: Int): List<Issue>
}
