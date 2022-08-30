package com.example.baleproject.data.model

import java.lang.IllegalStateException

enum class IssueType {
    Bug,
    Suggestion;

    fun get(name: String): IssueType {
        return values().firstOrNull {
            it.name == name
        } ?: throw IllegalStateException("Unknown Issue")
    }
}