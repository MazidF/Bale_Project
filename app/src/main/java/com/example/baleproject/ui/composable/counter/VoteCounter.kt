package com.example.baleproject.ui.composable.counter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.baleproject.R
import com.example.baleproject.data.model.Vote
import com.example.baleproject.ui.composable.text.TitleText

@Composable
fun VoteCounter(vote: Vote) {
    val isUp = vote is Vote.Up
    Surface {
        Row {
            Image(
                painter = painterResource(
                    id = if (isUp) {
                        R.drawable.ic_up
                    } else {
                        R.drawable.ic_down
                    }
                ),
                contentDescription = null,
                colorFilter = ColorFilter.tint(vote.color),
            )

            Spacer(modifier = Modifier.width(2.dp))

            TitleText(
                text = vote.vote().toString(),
            )
        }
    }
}