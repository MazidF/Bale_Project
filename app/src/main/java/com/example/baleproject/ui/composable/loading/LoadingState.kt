package com.example.baleproject.ui.composable.loading

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.example.baleproject.R
import com.example.baleproject.ui.theme.BaleProjectTheme
import com.example.baleproject.utils.isAppending
import com.example.baleproject.utils.isRefreshing

@Composable
fun <T : Any> PagingLoadingState(
    lazyPagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    context: LazyListScope.() -> Unit,
) {
    val state = lazyPagingItems.loadState

    if (state.isRefreshing()) {
        LottieAnimationCustomized(id = R.raw.three_dot_loading)
    } else {
        LazyColumn(
            modifier = modifier,
        ) {
            context()
            if (state.isAppending()) {
                item {
                    AppendingLoading()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BaleProjectTheme {
        Surface {
            LottieAnimationCustomized(id = R.raw.three_dot_loading)
        }
    }
}
