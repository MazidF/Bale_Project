package com.example.baleproject.ui.composable.wrapper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurveLazyColumn(
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 70.dp)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            )
            .padding(horizontal = 17.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content,
    )
}