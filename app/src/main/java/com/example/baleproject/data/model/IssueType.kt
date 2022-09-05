package com.example.baleproject.data.model

enum class IssueType(
    val query: String?,
) {
    Bug("Bug"),
    Suggestion("Suggestion"),
    None(null);

    fun get(name: String): IssueType {
        return values().firstOrNull {
            it.name == name
        } ?: throw IllegalStateException("Unknown Issue")
    }
}