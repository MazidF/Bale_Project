package com.example.baleproject.data.model

enum class SortType {
    ASC,
    DESC;

    companion object {
        fun get(name: String): SortType {
            return values().firstOrNull {
                it.name == name
            } ?: throw IllegalStateException("Unknown SortType")
        }
    }
}