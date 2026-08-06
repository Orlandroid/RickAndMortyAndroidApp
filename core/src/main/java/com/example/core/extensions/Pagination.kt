package com.example.core.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.core.ui.components.LoadingNextPageItem
import com.example.core.ui.components.PageLoader


@Composable
fun <T : Any> LazyPagingItems<T>.LoadState(
    modifier: Modifier = Modifier,
    config: LoadStateConfig = LoadStateConfig()
) {
    when {
        loadState.refresh is LoadState.Loading -> {
            if (config.initialLoading == null) {
                PageLoader(modifier = modifier)
            } else {
                config.initialLoading()
            }
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

data class LoadStateConfig(val initialLoading: @Composable (() -> Unit)? = null)