package com.rickandmortyorlando.orlando.features.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.rickandmortyorlando.orlando.components.Toolbar
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration


@Composable
fun BaseComposeScreen(
    toolbarConfiguration: ToolbarConfiguration = ToolbarConfiguration(),
    background: Color = Color.White,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            if (toolbarConfiguration.showToolbar) {
                Toolbar(
                    toolbarConfiguration = toolbarConfiguration,
                )
            }
        }
    ) { paddingValues ->
        ContentScreen(
            paddingValues = paddingValues,
            background = background
        ) {
            content()
        }
    }
}


@Composable
private fun ContentScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    background: Color,
    content: @Composable () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(background)
    ) {
        content.invoke()
    }
}


@Composable
@Preview(showBackground = true)
fun BaseViewPreview() {
    BaseComposeScreen {
        Text(text = "I am just trying to be my best software engineer version")
    }
}