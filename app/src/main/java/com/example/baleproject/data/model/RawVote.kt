package com.example.baleproject.data.model

class RawVote private constructor(
    val type: String,
) {
    companion object {
        fun get(voteUp: Boolean): RawVote {
            return if (voteUp) {
                RawVote("Up")
            } else {
                RawVote("Down")
            }
        }
    }
}