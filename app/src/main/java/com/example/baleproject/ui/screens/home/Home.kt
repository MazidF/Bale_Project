package com.example.baleproject.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.baleproject.data.model.IssueStatus
import com.example.baleproject.ui.composable.DescriptionText
import com.example.baleproject.ui.composable.IssueItemView
import com.example.baleproject.ui.composable.TitleText
import com.example.baleproject.ui.model.IssueItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@Composable
fun Home() {
    HomeState()
}


@Composable
private fun HomeState(viewModel: HomeViewModel = hiltViewModel()) {
    val pagerPagesItems = IssueStatus.values().map {
        viewModel.loadIssues(it).collectAsLazyPagingItems()
    }
    val titles = IssueStatus.values().map {
        it.title
    }
    val descriptions = IssueStatus.values().map {
        it.description
    }

    HomeContent(
        titles = titles,
        descriptions = descriptions,
        pagerPagesItems = pagerPagesItems,
    )
}

@Composable
private fun HomeContent(
    titles: List<String>,
    descriptions: List<String>,
    pagerPagesItems: List<LazyPagingItems<IssueItem>>,
) {
    PagerState(
        titles = titles,
        descriptions = descriptions,
        pagingItems = pagerPagesItems
    )
}


@Composable
private fun PagerState(
    titles: List<String>,
    descriptions: List<String>,
    pagingItems: List<LazyPagingItems<IssueItem>>,
) {
    PagerContent(
        titles = titles,
        descriptions = descriptions,
        pagingItems = pagingItems,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PagerContent(
    titles: List<String>,
    descriptions: List<String>,
    pagingItems: List<LazyPagingItems<IssueItem>>,
) {
    HorizontalPager(
        count = pagingItems.size,
    ) { currentPage ->
        PagerPage(
            title = titles[currentPage],
            description = descriptions[currentPage],
            pagingItem = pagingItems[currentPage],
        )
    }
}

@Composable
private fun PagerPage(
    title: String,
    description: String,
    pagingItem: LazyPagingItems<IssueItem>,
) {
    val loadState = pagingItem.loadState
    LazyColumn {
        item {
            PageTitle(title, description)
        }

        if (loadState.refresh == LoadState.Loading) {
            item {
                PageRefreshLoading()
            }
        }

        items(pagingItem, key = { it }) { issue ->
            issue?.let {
                IssueItemView(issue = it)
            }
        }

        if (loadState.append == LoadState.Loading) {
            item {
                PageAppendLoading()
            }
        }
    }
}

@Composable
fun PageRefreshLoading() {

}

@Composable
fun PageAppendLoading() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
    )
}

@Composable
fun PageTitle(title: String, description: String) {
    TitleText(text = title)
    Spacer(modifier = Modifier.height(10.dp))
    DescriptionText(text = description)
}
