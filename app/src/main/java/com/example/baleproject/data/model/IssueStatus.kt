package com.example.baleproject.data.model

enum class IssueStatus {
    Pending,
    InProgress,
    Done;

    companion object {
        fun get(name: String): IssueStatus {
            return values().firstOrNull {
                it.name == name
            } ?: throw IllegalStateException("Unknown Issue")
        }
    }
}