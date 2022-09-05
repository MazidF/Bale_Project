package com.example.baleproject.data.local.db.converter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter
import com.example.baleproject.data.model.Vote
import com.google.gson.Gson

class Converter {

    @TypeConverter
    fun List<String>.string(): String {
        return joinToString(separator)
    }

    @TypeConverter
    fun String.toListString(): List<String> {
        return split(separator)
    }

    @TypeConverter
    fun Vote.toJsonString(): String {
        return Gson().toJson(this)
    }

    @TypeConverter
    fun String.jsonStringToVote(): Vote {
        return Gson().fromJson(this, Vote::class.java)
    }

    @TypeConverter
    fun Color.toInt(): Int {
        return toArgb()
    }

    @TypeConverter
    fun Int.toColor(): Color {
        return Color(this)
    }

    companion object {
        private const val separator = ", "
    }
}