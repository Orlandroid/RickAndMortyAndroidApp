package com.example.paggingexample.data.state

sealed class ApiState<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : ApiState<T>(data)
    class Loading<T>(data: T? = null) : ApiState<T>(data)
    class Error<T>(msg: Throwable, data: T? = null) : ApiState<T>(data, msg.toString())
    class ErrorNetwork<T> : ApiState<T>()
    class NoData<T>: ApiState<T>()
}