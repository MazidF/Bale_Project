package com.example.baleproject.ui.composable.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.baleproject.R
import com.example.baleproject.data.model.IssueStatus
import com.example.baleproject.data.model.IssueType
import com.example.baleproject.data.model.SortBy
import com.example.baleproject.data.model.SortType
import com.example.baleproject.ui.composable.item.ExpandableOption
import com.example.baleproject.ui.composable.tapbor.TabItem
import com.example.baleproject.ui.composable.tapbor.TabLayout
import com.example.baleproject.ui.theme.blue

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ListOptions(
    initialTabIndex: Int,
    initialState: ListOptionsState,
    onDismiss: () -> Unit,
    onApply: (ListOptionsState) -> Unit,
) {
    val state = rememberListOptionsState(initialState = initialState.copy())
    var currentTabIndex by remember { mutableStateOf(initialTabIndex) }

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(10.dp),
        onDismissRequest = onDismiss,
        title = {
            TabLayout(
                initialSelectedIndex = initialTabIndex,
                tabs = listOf(
                    TabItem("Filter", R.drawable.ic_filter),
                    TabItem("Sort", R.drawable.ic_sort),
                ),
                onTabIndexChanged = { tabIndex ->
                    currentTabIndex = tabIndex
                }
            )
        },
        buttons = {
            val filterIsVisible = currentTabIndex == 0

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (filterIsVisible) {
                    FilterPage(
                        currentIssueType = state.currentIssueType,
                        currentIssueStatus = state.currentIssueStatus,
                        onStateChanged = {
                            state.currentIssueType = it.first
                            state.currentIssueStatus = it.second
                        }
                    )
                } else {
                    SortPage(
                        currentSortType = state.currentSortType,
                        currentSortBy = state.currentSortBy,
                        onStateChanged = {
                            state.currentSortBy = it.first
                            state.currentSortType = it.second
                        }
                    )
                }
                Button(
                    onClick = {
                        onApply(state)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = blue,
                    ),
                    modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                ) {
                    Text(
                        text = stringResource(R.string.apply),
                        color = Color.White,
                    )
                }
            }
        }
    )
}

@Composable
private fun SortPage(
    currentSortType: SortType?,
    currentSortBy: SortBy?,
    onStateChanged: (Pair<SortBy?, SortType?>) -> Unit,
) {
    var sortType by remember { mutableStateOf(currentSortType) }
    var sortBy by remember { mutableStateOf(currentSortBy) }

    LaunchedEffect(key1 = sortBy, key2 = sortType) {
        val pair = Pair(sortBy, sortType)
        onStateChanged(pair)
    }

    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        SortType(sortType) {
            sortType = it
        }
        SortBy(sortBy) {
            sortBy = it
        }
    }
}

@Composable
private fun SortBy(
    sortBy: SortBy?,
    onSortByChanged: (SortBy?) -> Unit,
) {
    val items = SortBy.values().map { it.name }

    ExpandableOption(
        iconId = R.drawable.ic_sort,
        label = " Sort By",
        options = items,
        checkedOptionIndex = sortBy?.ordinal,
        selectedOptionIndex = { index ->
            onSortByChanged(
                SortBy.values().firstOrNull { it.ordinal == index }
            )
        }
    )
}

@Composable
private fun SortType(
    sortType: SortType?,
    onSortTypeChanged: (SortType?) -> Unit,
) {
    val items = SortType.values().map { it.name }

    ExpandableOption(
        iconId = R.drawable.ic_sort,
        label = "Sort Type",
        options = items,
        checkedOptionIndex = sortType?.ordinal,
        selectedOptionIndex = { index ->
            onSortTypeChanged(
                SortType.values().firstOrNull { it.ordinal == index }
            )
        }
    )
}

@Composable
private fun FilterPage(
    currentIssueType: IssueType?,
    currentIssueStatus: IssueStatus?,
    onStateChanged: (Pair<IssueType?, IssueStatus?>) -> Unit,
) {
    var issueType by remember { mutableStateOf(currentIssueType) }
    var issueStatus by remember { mutableStateOf(currentIssueStatus) }

    LaunchedEffect(key1 = issueType, key2 = issueStatus) {
        val pair = Pair(issueType, issueStatus)
        onStateChanged(pair)
    }

    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        FilterType(issueType) {
            issueType = it
        }
        FilterStatus(issueStatus) {
            issueStatus = it
        }
    }
}

@Composable
private fun FilterType(
    issueType: IssueType?,
    onTypeChanged: (IssueType?) -> Unit,
) {
    val items = IssueType.values().map(IssueType::name)

    ExpandableOption(
        iconId = R.drawable.ic_sort,
        label = "Issue Type",
        options = items,
        checkedOptionIndex = issueType?.ordinal,
        selectedOptionIndex = { index ->
            onTypeChanged(
                IssueType.values().firstOrNull { it.ordinal == index }
            )
        }
    )
}

@Composable
private fun FilterStatus(
    issueStatus: IssueStatus?,
    onStatusChanged: (IssueStatus?) -> Unit,
) {
    val items = IssueStatus.values().map(IssueStatus::name)

    ExpandableOption(
        iconId = R.drawable.ic_sort,
        label = "Issue Status",
        options = items,
        checkedOptionIndex = issueStatus?.ordinal,
        selectedOptionIndex = { index ->
            onStatusChanged(
                IssueStatus.values().firstOrNull { it.ordinal == index }
            )
        }
    )
}

@Composable
fun rememberListOptionsState(initialState: ListOptionsState): ListOptionsState {
    return rememberSaveable(saver = ListOptionsState.saver) {
        initialState
    }
}

@Stable
data class ListOptionsState(
    var currentIssueType: IssueType? = null,
    var currentIssueStatus: IssueStatus? = null,
    var currentSortType: SortType? = null,
    var currentSortBy: SortBy? = null,
) {

    companion object {
        val saver = object : Saver<ListOptionsState, List<String?>> {
            override fun restore(value: List<String?>): ListOptionsState {
                return ListOptionsState(
                    currentIssueStatus = value[0]?.let { IssueStatus.get(it) },
                    currentIssueType = value[1]?.let { IssueType.get(it) },
                    currentSortType = value[2]?.let { SortType.get(it) },
                    currentSortBy = value[3]?.let { SortBy.get(it) },
                )
            }

            override fun SaverScope.save(value: ListOptionsState): List<String?> {
                return listOf(
                    value.currentIssueStatus?.name,
                    value.currentIssueType?.name,
                    value.currentSortType?.name,
                    value.currentSortBy?.name,
                )
            }
        }
    }
}

@Preview
@Composable
fun ListOptionPreview() = Surface {
    ListOptions(
        initialTabIndex = 0,
        initialState = ListOptionsState(),
        onDismiss = { /*TODO*/ },
        onApply = {}
    )
}
