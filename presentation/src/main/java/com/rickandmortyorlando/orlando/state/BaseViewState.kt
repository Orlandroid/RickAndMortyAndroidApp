package com.rickandmortyorlando.orlando.state

sealed class BaseViewState<out T> {
    data object Loading : BaseViewState<Nothing>()
    data class Content<T>(val result: T) : BaseViewState<T>()
    data class Error(val message: String) : BaseViewState<Nothing>()
}