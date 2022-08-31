package com.example.baleproject.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baleproject.R
import com.example.baleproject.data.model.Comment
import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.Vote
import com.example.baleproject.ui.model.IssueItem

@Composable
fun LabelItem(label: Label) {
    Surface(
        shape = RoundedCornerShape(5.dp),
        color = Color(198, 205, 207, 255),
        modifier = Modifier.padding(5.dp),
    ) {
        Text(
            text = label.name,
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            color = label.color,
//            color = Color(17, 95, 146, 255),
        )
    }
}

@Composable
fun IssueItemView(issue: IssueItem) {
    ItemCardView {
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
                VoteCounterView(issue.vote)

                Spacer(modifier = Modifier.weight(1f))

                CommentCounter(count = issue.commentCounts)
            }
        }
    }
}

@Composable
fun LabelList(labels: List<Label>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        labels.forEach { label ->
            LabelItem(label = label)
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun DescriptionText(text: String, maxLines: Int = 2) {
    Text(
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.alpha(0.7f),
    )
}

@Composable
fun CommentItemView(comment: Comment) {
    ItemCardView {

    }
}

@Composable
fun ItemCardView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface,
        content = {
            Surface(
                color = Color(0xFF3798CC),
                shape = RectangleShape,
                modifier = Modifier.height(6.dp),
            ) {}
            content()
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = {},
            )
    )
}

@Composable
fun VoteCounterView(vote: Vote) {
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
                text = if (isUp) {
                    vote.upVoteCount
                } else {
                    vote.downVoteCount
                }.toString(),
            )
        }
    }
}

@Composable
fun CommentCounter(count: Int) {
    Surface {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_comment),
                colorFilter = ColorFilter.tint(Color.LightGray),
                modifier = Modifier.alpha(.7f),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = count.toString()
            )
        }
    }
}
