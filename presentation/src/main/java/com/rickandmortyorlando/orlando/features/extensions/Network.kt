package com.rickandmortyorlando.orlando.features.extensions

import retrofit2.Response


fun <T> Response<T>?.getErrorBody(): String? {
    return this?.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }
}