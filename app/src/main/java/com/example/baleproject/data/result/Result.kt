package com.example.baleproject.data.result

import com.example.baleproject.data.result.error.Error
import com.example.baleproject.utils.UNKNOWN
import retrofit2.Response

sealed class Result<T>(
    protected val data: T? = null,
    protected val error: Error? = null,
) {
    class Loading<T> : Result<T>()

    class Success<T>(data: T) : Result<T>(data = data) {
        fun data(): T {
            return data!!
        }
    }

    class Fail<T>(error: Error) : Result<T>(error = error) {
        fun error(): Error {
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
        fun <T> fail(error: Throwable) = Fail<T>(error = Error(error))
        fun <T> fail(msg: String?) = Fail<T>(error = Error(msg ?: UNKNOWN))

        fun <T> from(response: Response<T>): Result<T> {
            return if (response.isSuccessful and (response.body() != null)) {
                success(response.body()!!)
            } else {
                fail(response.errorBody()?.string())
            }
        }

        fun <T, R> from(response: Response<T>, transformer: T.() -> R): Result<R> {
            return if (response.isSuccessful and (response.body() != null)) {
                success(response.body()!!.transformer())
            } else {
                fail(response.errorBody()?.string())
            }
        }
    }
}
