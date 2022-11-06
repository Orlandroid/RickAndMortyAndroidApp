package com.example.paggingexample.utils

import android.content.Context
import com.example.paggingexample.R
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun getColorStatus(status: String, context: Context): Int {
    return when (status) {
        "Alive" -> {
            context.resources.getColor(R.color.alive)
        }
        "Dead" -> {
            context.resources.getColor(R.color.dead)
        }
        "unknown" -> {
            context.resources.getColor(R.color.unknown)
        }
        else -> {
            context.resources.getColor(R.color.unknown)
        }
    }
}

sealed class ErrorType {
    class HttpException(
        errorBody: ResponseBody?,
        messageError: String,
        val responseCode: Int
    ) :
        ErrorType()

    class SocketTimeoutException(messageError: String) : ErrorType()
    class UnknownHostException(messageError: String) : ErrorType()
    object Unknown : ErrorType()
}

fun getTypeOfError(exception: Exception): ErrorType {
    when (exception) {
        is HttpException -> {
            val errorBody = exception.response()?.errorBody()
            val errorCode = exception.response()?.code()
            return ErrorType.HttpException(errorBody, exception.message(), errorCode ?: -1)
        }
        is SocketTimeoutException -> {
            return ErrorType.SocketTimeoutException(exception.message ?: "")
        }
        is UnknownHostException -> {
            return ErrorType.UnknownHostException(exception.message ?: "")
        }
        else -> {
            return ErrorType.Unknown
        }
    }
}

