package com.example.baleproject.data.model

enum class SortBy {
    DATE,
    VOTES;

    companion object {
        fun get(name: String): SortBy {
            return values().firstOrNull {
                it.name == name
            } ?: throw IllegalStateException("Unknown SortBy")
        }
    }
}