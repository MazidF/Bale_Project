package com.example.baleproject.data.repository

import com.example.baleproject.data.UserDataSource
import kotlinx.coroutines.CoroutineDispatcher

class UserRepository(
    private val dataSource: UserDataSource,
    private val dispatcher: CoroutineDispatcher,
)