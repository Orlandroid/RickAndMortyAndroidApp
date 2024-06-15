package com.rickandmortyorlando.orlando.utils

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.rickandmortyorlando.orlando.R

@Composable
fun AppProgress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .width(64.dp)
            .height(64.dp),
        color = colorResource(id = R.color.progress_color)
    )
}