package com.rickandmortyorlando.orlando.features.base

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

@Composable
fun SmallSpacer(orientation: Orientation) {
    BaseSpacer(orientation = orientation, size = 8.dp)
}

@Composable
fun MediumSpacer(orientation: Orientation) {
    BaseSpacer(orientation = orientation, size = 16.dp)
}

@Composable
fun LongSpacer(orientation: Orientation) {
    BaseSpacer(orientation = orientation, size = 24.dp)
}

@Composable
fun BaseSpacer(orientation: Orientation, size: Dp) {
    when (orientation) {
        Orientation.VERTICAL -> {
            Spacer(modifier = Modifier.height(size))
        }

        Orientation.HORIZONTAL -> {
            Spacer(modifier = Modifier.width(size))
        }
    }
}


enum class Orientation {
    VERTICAL,//height
    HORIZONTAL//width
}



