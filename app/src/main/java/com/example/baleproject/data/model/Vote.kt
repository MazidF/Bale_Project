package com.example.baleproject.data.model

import androidx.compose.ui.graphics.Color
import java.io.Serializable

sealed class Vote(
    val upVoteCount: Int,
    val downVoteCount: Int,
    val color: Color
) : Serializable {
    class Up(
        upVoteCount: Int,
        downVoteCount: Int,
    ) : Vote(upVoteCount, downVoteCount, Color.Green)

    class Down(
        upVoteCount: Int,
        downVoteCount: Int,
    ) : Vote(upVoteCount, downVoteCount, Color.Red)

    fun vote(): Int {
        return upVoteCount - downVoteCount
    }

    companion object {
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

        fun get(upVoteCount: Int, downVoteCount: Int): Vote {
            return if (upVoteCount < downVoteCount) {
                Down(upVoteCount, downVoteCount)
            } else {
                Up(upVoteCount, downVoteCount)
            }
        }
    }
}