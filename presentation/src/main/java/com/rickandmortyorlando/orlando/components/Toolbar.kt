package com.rickandmortyorlando.orlando.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.rickandmortyorlando.orlando.theme.AlwaysWhite
import com.rickandmortyorlando.orlando.theme.StatusBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    toolbarConfiguration: ToolbarConfiguration
) {
    TopAppBar(
        colors =
            topAppBarColors(
                containerColor = toolbarConfiguration.toolbarBackgroundColor,
                titleContentColor = toolbarConfiguration.toolbarTextColor,
            ),
        title = {
            Text(
                toolbarConfiguration.title,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            toolbarConfiguration.actions?.let { it() }
        },
        navigationIcon = {
            if (toolbarConfiguration.showBackIcon) {
                IconButton(onClick = toolbarConfiguration.clickOnBackButton) {
                    Icon(
                        tint = AlwaysWhite,
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            }
        }
    )
}

data class ToolbarConfiguration(
    val showToolbar: Boolean = true,
    val title: String = "Android Developer",
    val showBackIcon: Boolean = true,
    val toolbarBackgroundColor: Color = StatusBarColor,
    val toolbarTextColor: Color = AlwaysWhite,
    val actions: @Composable (RowScope.() -> Unit?)? = null,
    val clickOnBackButton: () -> Unit = {}
)


@Preview(showBackground = true)
@Composable
private fun SimpleComposablePreview() {
    Toolbar(
        toolbarConfiguration = ToolbarConfiguration(title = "Android Developer", actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }
        )
    )
}
