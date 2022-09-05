package com.example.baleproject.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.baleproject.data.local.db.converter.Converter
import com.example.baleproject.data.model.Issue

@Database(
    entities = [
        Issue::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    Converter::class,
)
abstract class DataBase : RoomDatabase()