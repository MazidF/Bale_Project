package com.example.baleproject.ui.composable.loading

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baleproject.ui.theme.blue

@Composable
fun AppendingLoading() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        color = blue,
    )
}