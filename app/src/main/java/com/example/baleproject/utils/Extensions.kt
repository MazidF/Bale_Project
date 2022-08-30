package com.example.baleproject.utils

import com.example.baleproject.data.result.Result
import retrofit2.Response

fun <T : Any> Response<T>.asResult(): Result<T> {
    return Result.from(this)
}