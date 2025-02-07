package com.rickandmortyorlando.orlando.features.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rickandmortyorlando.orlando.components.LoadingNextPageItem
import com.orlando.androidbase.presentation.features.components.PageLoader

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

@Composable
fun <T : Any> LazyPagingItems<T>.LoadState(modifierPageLoader: Modifier = Modifier) {
    when {
        loadState.refresh is LoadState.Loading -> {
            PageLoader(modifier = modifierPageLoader)
        }

        loadState.refresh is LoadState.Error -> {
            Text("Error") //Add one generic error screen
        }

        loadState.append is LoadState.Loading -> {
            Spacer(Modifier.height(16.dp))
            LoadingNextPageItem(modifier = Modifier)

        }

        loadState.append is LoadState.Error -> {
            Text("Error") //Add one generic error screen
        }
    }
}