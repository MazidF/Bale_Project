package com.example.baleproject.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.baleproject.data.model.Label
import com.example.baleproject.data.model.Vote
import com.example.baleproject.ui.composable.IssueItemView
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.ui.navigation.Destinations
import com.example.baleproject.ui.navigation.Navigation
import com.example.baleproject.ui.theme.BaleProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BaleProjectTheme {
                Surface {
                    Navigation(
                        navController = navController,
                        startDestination = Destinations.Home,
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Preview(
        uiMode = UI_MODE_NIGHT_YES,
        showBackground = true,
    )
    @Composable
    fun Preview() {
        BaleProjectTheme {
            Surface {
                IssueItemView(
                    issue = IssueItem(
                        id = "",
                        title = "Add tags for students",
                        description = "Some text for filling text view and make text to use it's overflow something else also i s here why is 1234",
                        vote = Vote.Up(0, 0),
                        reviewed = true,
                        labels = List(5) {
                            Label(
                                id = it.toString(),
                                name = "item length$it",
                                color = Color.Gray,
                            )
                        },
                        commentCounts = 10
                    )
                )
            }
        }
    }
}