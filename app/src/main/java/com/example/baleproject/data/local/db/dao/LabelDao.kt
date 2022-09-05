package com.example.baleproject.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.baleproject.data.model.Label
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface LabelDao : IDao<Label> {

    @Query("select * from label_table where id = :id")
    suspend fun get(id: Long): Label

    @Query("select * from label_table")
    fun getAll(): Flow<List<Label>>

    @Query("select * from label_table limit :limit offset :offset")
    fun getAll(limit: Int, offset: Int): List<Label>
}*/
