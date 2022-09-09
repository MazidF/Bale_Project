package com.example.baleproject.ui.composable.item

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.RawVote
import com.example.baleproject.data.model.Vote
import com.example.baleproject.ui.composable.counter.CommentCounter
import com.example.baleproject.ui.composable.counter.MutableVoteCounter
import com.example.baleproject.ui.composable.counter.VoteCounter
import com.example.baleproject.ui.composable.list.LabelList
import com.example.baleproject.ui.composable.shimmer.shapes.BoxShimmer
import com.example.baleproject.ui.composable.text.DescriptionText
import com.example.baleproject.ui.composable.text.TitleText
import com.example.baleproject.ui.composable.wrapper.ItemCardComposeWithColorLine
import com.example.baleproject.ui.model.IssueItem

@Composable
fun IssueItemCompose(issue: Issue, onClick: () -> Unit) {
    ItemCardComposeWithColorLine(
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            TitleText(text = issue.title)

            Spacer(modifier = Modifier.height(5.dp))

            DescriptionText(text = issue.description)

/*            Spacer(modifier = Modifier.height(7.dp))

            LabelList(labels = issue.labels)*/

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                VoteCounter(issue.vote)

                Spacer(modifier = Modifier.weight(1f))

                CommentCounter(count = issue.commentsCount)
            }
        }
    }
}


@Composable
fun IssueItemCompose(
    issue: IssueItem,
    isLoading: Boolean,
    onVote: (RawVote) -> Unit,
) {
    val item by remember { mutableStateOf(issue) }
    val loading by remember { mutableStateOf(isLoading) }

    ItemCardComposeWithColorLine {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            TitleText(text = item.title)

            Spacer(modifier = Modifier.height(5.dp))

            DescriptionText(text = item.description)

            if (item.labels.isNotEmpty()) {
                Spacer(modifier = Modifier.height(7.dp))

                LabelList(labels = item.labels)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                MutableVoteCounter(
                    vote = item.vote,
                    isLoading = loading,
                    onVote = onVote
                )

                Spacer(modifier = Modifier.weight(1f))

                CommentCounter(count = item.commentsCount)
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

@Preview
@Composable
fun LabelItemPreview() {
    IssueItemCompose(
        issue = Issue(
            "",
            title = "Creating a NavHost in Compose Android",
            vote = Vote.Up(23, 12),
            reviewed = false,
            commentsCount = 0,
            description = "Each NavController must be associated with a single NavHost composable. The NavHost links the NavController with a navigation graph that specifies the composable destinations that you should be able to navigate between. As you navigate between composables, the content of the NavHost is automatically recomposed. Each composable destination in your navigation graph is associated with a route.",
            labelIds = listOf(),
        ),
    ) {

    }
}
