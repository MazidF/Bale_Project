package com.example.baleproject.ui.composable.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baleproject.R
import com.example.baleproject.data.model.Comment
import com.example.baleproject.ui.composable.shimmer.shapes.BoxShimmer
import com.example.baleproject.ui.composable.shimmer.shapes.CircleShimmer
import com.example.baleproject.ui.composable.wrapper.ItemCardCompose
import com.example.baleproject.ui.theme.blue

@Composable
fun CommentItemCompose(
    comment: Comment,
    onReply: () -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }

    ItemCardCompose(
        onClick = {
            isExpanded = isExpanded.not()
        },
    ) {
        Column(
            Modifier
                .padding(10.dp),
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.male_user),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp, 50.dp),
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = comment.userId)

                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    onClick = onReply,
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(text = "Reply", color = blue)
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                overflow = TextOverflow.Ellipsis,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                text = comment.text,
                modifier = Modifier
                    .padding(5.dp),
                color = Color.Gray,
                fontSize = 13.sp,
            )
        }
    }
}


@Composable
fun ShimmerCommentItemCompose() {

    ItemCardCompose {
        Column(
            Modifier
                .padding(10.dp),
        ) {
            Row {
                CircleShimmer(size = 50.dp)

                Spacer(modifier = Modifier.width(10.dp))

                BoxShimmer(width = 200.dp, height = 20.dp)
            }

            Spacer(modifier = Modifier.height(10.dp))


            BoxShimmer(height = 20.dp)

            Spacer(modifier = Modifier.height(3.dp))

            BoxShimmer(height = 20.dp)

            Spacer(modifier = Modifier.height(3.dp))

            BoxShimmer(height = 20.dp, width = 300.dp)
        }
    }
}

@Preview
@Composable
fun Preview() {
    val comment = Comment(
        id = "12",
        userId = "user23",
        text = "Funny Tik Toks that will make you laugh, The funniest TikTok compilations with Tik Toks Mashups 2020/2021/2022. Other Tik Tok channels making quality, TikTok Charts, Visicks, Azure, Wifi Plug, Monstro, and TikHQ. Making the best Tik Tok compilations. ",
        date = "System.currentTimeMillis() / 1000"
    )
    CommentItemCompose(comment)
}