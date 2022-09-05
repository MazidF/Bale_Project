package com.example.baleproject.ui.composable.item

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baleproject.ui.composable.counter.CommentCounter
import com.example.baleproject.ui.composable.counter.VoteCounter
import com.example.baleproject.ui.composable.list.LabelList
import com.example.baleproject.ui.composable.shimmer.shapes.BoxShimmer
import com.example.baleproject.ui.composable.shimmer.shapes.CircleShimmer
import com.example.baleproject.ui.composable.text.DescriptionText
import com.example.baleproject.ui.composable.text.TitleText
import com.example.baleproject.ui.composable.wrapper.ItemCardComposeWithColorLine
import com.example.baleproject.ui.model.IssueItem

@Composable
fun IssueItemCompose(issue: IssueItem) {
    ItemCardComposeWithColorLine {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            TitleText(text = issue.title)

            Spacer(modifier = Modifier.height(5.dp))

            DescriptionText(text = issue.description)

            Spacer(modifier = Modifier.height(7.dp))

            LabelList(labels = issue.labels)

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                VoteCounter(issue.vote)

                Spacer(modifier = Modifier.weight(1f))

                CommentCounter(count = issue.commentCounts)
            }
        }
    }
}


@Composable
fun ShimmerIssueItemCompose() {
    ItemCardComposeWithColorLine {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            BoxShimmer(width = 150.dp, height = 20.dp)

            Spacer(modifier = Modifier.height(5.dp))

            BoxShimmer(width = 220.dp, height = 20.dp)

            Spacer(modifier = Modifier.height(15.dp))

            Row {
                BoxShimmer(width = 65.dp, height = 26.dp)
                Spacer(modifier = Modifier.width(5.dp))
                BoxShimmer(width = 65.dp, height = 26.dp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                BoxShimmer(width = 50.dp, height = 25.dp)

                Spacer(modifier = Modifier.weight(1f))

                BoxShimmer(width = 50.dp, height = 25.dp)
            }
        }
    }
}