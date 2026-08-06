package com.example.core.util

import androidx.compose.ui.graphics.Color
import com.example.core.ui.theme.Alive
import com.example.core.ui.theme.Dead
import com.example.core.ui.theme.Unknown

fun getColorStatusResource(status: String? = "unknown"): Color {
    return when (status) {
        "Alive" -> {
            Alive
        }

        "Dead" -> {
            Dead
        }

        "unknown" -> {
            Unknown
        }

        else -> {
            Unknown
        }
    }
}


fun String.getColorStatus(): Color {
    when (this) {
        "Alive" -> {
            return Alive
        }

        "Dead" -> {
            return Dead
        }

        "unknown" -> {
            return Unknown
        }

        else -> {
            return Unknown
        }
    }
}
