package com.example.baleproject.data.model

import androidx.compose.runtime.Stable

@Stable
enum class IssueType {
    Bug,
    Suggestion;

    companion object {
        fun get(name: String): IssueType {
            return values().firstOrNull {
                it.name == name
            } ?: throw IllegalStateException("Unknown Issue")
        }
    }
}