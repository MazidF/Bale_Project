package com.example.baleproject.data.result

import retrofit2.Response

sealed class Result<T : Any>(
    protected val data: T? = null,
    protected val error: Throwable? = null,
) {
    class Loading<T : Any> : Result<T>()

    class Success<T : Any>(data: T) : Result<T>(data = data) {
        fun data(): T {
            return data!!
        }
    }

    class Fail<T : Any>(error: Throwable) : Result<T>(error = error) {
        fun error(): Throwable {
            return error!!
        }
    }

    fun <R : Any> map(transformer: T.() -> R): Result<R> {
        return when (this) {
            is Loading -> loading()
            is Fail -> fail(error())
            is Success -> success(data().transformer())
        }
    }

    companion object {
        fun <T : Any> loading() = Loading<T>()
        fun <T : Any> success(data: T) = Success(data = data)
        fun <T : Any> fail(error: Throwable) = Fail<T>(error = error)

        fun <T : Any> from(response: Response<T>): Result<T> {
            return if (response.isSuccessful and (response.body() != null)) {
                success(response.body()!!)
            } else {
                fail(Exception(response.errorBody()?.string()))
            }
        }
    }
}
