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

fun getEmailAndPassword(context: Context): Pair<String, String>? {
    val sharedPreferences = sharedPreferences(context)
    val email = sharedPreferences.getString(USER_EMAIL_KEY, null)
    val pass = sharedPreferences.getString(USER_PASSWORD_KEY, null)
    if (email == null || pass == null) {
        return null
    }
    return Pair(email, pass)
}

fun updateEmailAndPassword(context: Context, email: String, password: String) {
    val sharedPreferences = sharedPreferences(context)
    sharedPreferences.edit {
        putString(USER_EMAIL_KEY, email)
        putString(USER_PASSWORD_KEY, password)
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
