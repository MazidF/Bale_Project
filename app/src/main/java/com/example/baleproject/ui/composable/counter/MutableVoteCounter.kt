package com.example.baleproject.ui.composable.counter

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baleproject.R
import com.example.baleproject.data.model.RawVote
import com.example.baleproject.data.model.Vote
import com.example.baleproject.ui.theme.blue

@Composable
fun MutableVoteCounter(
    vote: Vote,
    isLoading: Boolean,
    onVote: (RawVote) -> Unit
) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(3.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.animateContentSize(),
    ) {
        Row(
            modifier = Modifier
                .padding(3.dp)
                .animateContentSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CounterDownButton(enable = isLoading.not()) {
                onVote(RawVote.get(false))
            }
            if (isLoading) {
                CircularProgressIndicator(
                    color = blue,
                    modifier = Modifier.padding(horizontal = 3.dp),
                )
            } else {
                Text(
                    text = vote.vote().toString(),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                )
            }
            CounterUpButton(enable = isLoading.not()) {
                onVote(RawVote.get(true))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CounterUpButton(enable: Boolean, onClick: () -> Unit) {
    Card(
        onClick = { if (enable) onClick() },
        shape = RoundedCornerShape(3.dp),
        backgroundColor = Color(162, 224, 161, 255),
        modifier = Modifier
            .size(33.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_add),
            colorFilter = ColorFilter.tint(Color(10, 61, 5, 100)),
            modifier = Modifier.size(23.dp),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CounterDownButton(enable: Boolean, onClick: () -> Unit) {
    Card(
        onClick = { if (enable) onClick() },
        shape = RoundedCornerShape(3.dp),
        backgroundColor = Color(224, 168, 161, 255),
        modifier = Modifier
            .size(33.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_remove),
            colorFilter = ColorFilter.tint(Color(61, 12, 5, 100)),
            modifier = Modifier.size(23.dp),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun CounterUpButtonPreview() {
    MutableVoteCounter(
        vote = Vote.Up(1, 20),
        isLoading = false,
    ) {

    }
}
