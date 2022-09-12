package com.example.baleproject.data.model

import androidx.compose.runtime.Stable

@Stable
data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val verified: Boolean,
    val cookie: String,
)