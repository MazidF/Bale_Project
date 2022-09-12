package com.example.baleproject.utils.chainrequest

import com.example.baleproject.data.result.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class Chain<I : Any, StartInput : Any> private constructor(
    private val request: ChainRequest<*, I>,
    firstBuilder: Chain<*, StartInput>?,
) {
    private var next: Chain<*, StartInput>? = firstBuilder

    fun <O : Any> then(request: ChainRequest<I, O>): Chain<O, StartInput> {
        return Chain(request, next ?: this).also {
            next = it
        }
    }

    suspend fun launch(value: StartInput, scope: CoroutineScope): Result<I> {
        var result: Result<*>
        var input: Any = value
        var chain: Chain<*, StartInput>? = next ?: this
        this.next = null // breaking the chain

        return withContext(scope.coroutineContext) {
            try {
                do {
                    result = chain!!.request.launch(input)
                    input = if (result is Result.Success) {
                        (result as Result.Success<*>)
                    } else {
                        throw (result as Result.Fail).error()
                    }
                    chain = chain!!.next
                } while (chain != null)
                result
            } catch (e: Exception) {
                Result.fail<I>(e)
            }
        } as Result<I>
    }

    companion object {
        fun <I : Any, StartInput : Any> start(
            request: ChainRequest<StartInput, I>,
        ): Chain<I, StartInput> {
            return Chain(request, firstBuilder = null)
        }
    }
}