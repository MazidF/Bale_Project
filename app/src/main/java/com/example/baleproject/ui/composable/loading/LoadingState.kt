package com.example.baleproject.ui.composable.loading

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.baleproject.R
import com.example.baleproject.ui.theme.BaleProjectTheme
import com.example.baleproject.utils.isAppending
import com.example.baleproject.utils.isRefreshing

@Composable
fun <T : Any> PagingLoadingState(
    lazyPagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    context: LazyListScope.() -> Unit,
) {
    val loadStates = lazyPagingItems.loadState

    if (loadStates.isRefreshing()) {
        LottieAnimationCustomized(id = R.raw.three_dot_loading)
    } else {
        LazyColumn(
            modifier = modifier,
            state = state,
        ) {
            context()
            item {
                if (loadStates.isAppending()) {
                    AppendingLoading()
                } else {
                    Spacer(modifier = Modifier.height(60.dp))
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
