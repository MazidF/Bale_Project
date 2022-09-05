package com.example.baleproject.utils.chainrequest

import com.example.baleproject.data.result.Result

abstract class ChainRequest<I, O> {
    @JvmName("launchAutoCaster")
    suspend fun <T> launch(input: T): Result<O> {
        return launch(input as I)
    }

    abstract suspend fun launch(input: I): Result<O>
}