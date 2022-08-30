package com.example.baleproject.data.result

import retrofit2.Response

sealed class Result<T>(
    protected val data: T? = null,
    protected val error: Throwable? = null,
) {
    class Loading<T> : Result<T>()

    class Success<T>(data: T) : Result<T>(data = data) {
        fun data(): T {
            return data!!
        }
    }

    class Fail<T>(error: Throwable) : Result<T>(error = error) {
        fun error(): Throwable {
            return error!!
        }
    }

    fun <R> map(transformer: T.() -> R): Result<R> {
        return when (this) {
            is Loading -> loading()
            is Fail -> fail(error())
            is Success -> success(data().transformer())
        }
    }

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data = data)
        fun <T> fail(error: Throwable) = Fail<T>(error = error)

        fun <T> from(response: Response<T>): Result<T> {
            return if (response.isSuccessful and (response.body() != null)) {
                success(response.body()!!)
            } else {
                fail(Exception(response.errorBody()?.string()))
            }
        }
    }
}
