package com.example.baleproject.data.model

sealed class Vote(
    val upVoteCount: Int,
    val downVoteCount: Int,
) {
    class Up(
        upVoteCount: Int,
        downVoteCount: Int,
    ) : Vote(upVoteCount, downVoteCount)

    class Down(
        upVoteCount: Int,
        downVoteCount: Int,
    ) : Vote(upVoteCount, downVoteCount)

    fun get(name: String, upVoteCount: Int, downVoteCount: Int): Vote {
        return when (name) {
            "Up" -> {
                Up(upVoteCount, downVoteCount)
            }
            "Down" -> {
                Down(upVoteCount, downVoteCount)
            }
            else -> throw IllegalStateException("Unknown vote")
        }
    }
}