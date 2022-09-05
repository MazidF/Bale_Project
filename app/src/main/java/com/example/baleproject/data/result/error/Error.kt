package com.example.baleproject.data.result.error

open class Error : Exception {
    constructor(msg: String) : super(msg)
    constructor(throwable: Throwable) : super(throwable)
}