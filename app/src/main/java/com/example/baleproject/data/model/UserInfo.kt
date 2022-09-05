package com.example.baleproject.data.model

data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val verified: Boolean,
    val accessToken: String,
)