package com.rickandmortyorlando.orlando.features.extensions

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun LoadState.Error.showError(onError: () -> Unit) {
    error.message?.let { message ->
        if (!message.contains("There is nothing here")) {
            onError.invoke()
        }
    }
}

fun CombinedLoadStates.getError(): LoadState.Error? {
    val errorState = when {
        this.append is LoadState.Error -> this.append as LoadState.Error
        this.prepend is LoadState.Error -> this.prepend as LoadState.Error
        this.refresh is LoadState.Error -> this.refresh as LoadState.Error
        else -> null
    }
    return errorState
}