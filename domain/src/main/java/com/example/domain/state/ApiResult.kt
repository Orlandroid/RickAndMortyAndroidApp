package com.example.domain.state

sealed class ApiResult<out T> {
    class Success<out T>(val data: T) : ApiResult<T>()
    class Error(val msg: String) : ApiResult<Nothing>()
}

fun <T> ApiResult<T>.isSuccess() = this is ApiResult.Success

fun <T> ApiResult<T>.isError() = this is ApiResult.Error

fun <T> ApiResult<T>.getMessage() = (this is ApiResult.Error)

fun <T> ApiResult<T>.getData() = (this as ApiResult.Success).data