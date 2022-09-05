package com.example.baleproject.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.baleproject.data.result.Result
import com.example.baleproject.ui.model.IssueItem
import com.example.baleproject.ui.navigation.Destinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

fun <T : Any> Response<T>.asResult(): Result<T> {
    return Result.from(this)
}

fun <T : Any, R : Any> Response<T>.asResult(transformer: T.() -> R): Result<R> {
    return Result.from(this, transformer)
}

fun sharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
}

fun getAccessToken(context: Context): String? {
    val sharedPreferences = sharedPreferences(context)
    return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
}

fun updateAccessToken(context: Context, token: String) {
    val sharedPreferences = sharedPreferences(context)
    sharedPreferences.edit {
        putString(ACCESS_TOKEN_KEY, token)
    }
}

fun getUserId(context: Context): String? {
    val sharedPreferences = sharedPreferences(context)
    return sharedPreferences.getString(USER_ID_KEY, null)
}

fun updateUserId(context: Context, token: String) {
    val sharedPreferences = sharedPreferences(context)
    sharedPreferences.edit {
        putString(USER_ID_KEY, token)
    }
}

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(block = block)
}

fun getDetailRouteByIssue(issueItem: IssueItem): String {
    return "${Destinations.Detail.name}/${issueItem.id}"
}

fun CombinedLoadStates.isRefreshing(): Boolean {
    return this.refresh == LoadState.Loading
}

fun CombinedLoadStates.isAppending(): Boolean {
    return this.append == LoadState.Loading
}

fun logger(msg: String, tag: String = "logger") {
    Log.d(tag, msg)
}
