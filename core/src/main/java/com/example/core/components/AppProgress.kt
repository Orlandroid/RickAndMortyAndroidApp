package com.example.core.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.theme.ProgressColor

@Composable
fun AppProgress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .width(64.dp)
            .height(64.dp),
        color = ProgressColor
    )
}

@Composable
@Preview(showBackground = true)
fun AppProgressPreview() {
    AppProgress()
}