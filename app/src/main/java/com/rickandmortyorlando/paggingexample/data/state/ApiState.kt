package com.rickandmortyorlando.paggingexample.data.state

sealed class ApiState<T>(val data: T? = null, val message: String? = null,val codeError: Int = -1) {

    class Success<T>(data: T) : ApiState<T>(data)
    class Loading<T>(data: T? = null) : ApiState<T>(data)
    class Error<T>(msg: Throwable, data: T? = null,codeError: Int=-1) : ApiState<T>(data, msg.toString(),codeError)
    class ErrorNetwork<T> : ApiState<T>()
    class NoData<T> : ApiState<T>()
}