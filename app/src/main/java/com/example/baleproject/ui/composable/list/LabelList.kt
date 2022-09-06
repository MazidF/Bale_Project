package com.example.baleproject.ui.composable.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.baleproject.data.model.Label
import com.example.baleproject.ui.composable.item.LabelItemCompose
import com.example.baleproject.ui.model.LabelItem

@Composable
fun LabelList(labels: List<LabelItem>) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        items(labels, key = { it.id }) { label ->
            LabelItemCompose(label = label)
        }
    }
}

