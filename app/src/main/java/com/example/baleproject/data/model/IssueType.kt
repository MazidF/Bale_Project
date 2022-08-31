package com.example.baleproject.data.model

import com.google.gson.annotations.SerializedName
import java.lang.IllegalStateException

enum class IssueType {
    Bug,
    Suggestion,
    @SerializedName("")
    None;

    fun get(name: String): IssueType {
        return values().firstOrNull {
            it.name == name
        } ?: throw IllegalStateException("Unknown Issue")
    }
}