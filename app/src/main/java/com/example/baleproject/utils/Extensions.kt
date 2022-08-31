package com.example.baleproject.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baleproject.data.model.User
import com.example.baleproject.data.result.Result
import com.example.baleproject.domain.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

fun <T : Any> Response<T>.asResult(): Result<T> {
    return Result.from(this)
}

fun sharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
}

fun UseCase.getAccessToken(context: Context): String? {
    val sharedPreferences = sharedPreferences(context)
    return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
}

fun UseCase.updateAccessToken(context: Context, token: String) {
    val sharedPreferences = sharedPreferences(context)
    sharedPreferences.edit {
        putString(ACCESS_TOKEN_KEY, token)
    }
}

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(block = block)
}
