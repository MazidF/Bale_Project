package com.example.baleproject.data.local.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface IDao<T : Any> {

    @Insert
    suspend fun insert(vararg items: T): List<Long>

    @Update
    suspend fun update(vararg items: T): Int

    @Delete
    suspend fun delete(vararg items: T): Int

}
