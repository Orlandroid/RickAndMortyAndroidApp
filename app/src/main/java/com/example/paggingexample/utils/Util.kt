package com.example.paggingexample.utils

import android.content.Context
import com.example.paggingexample.R


fun getColorStatus(status: String, context: Context): Int {
    return when (status) {
        "Alive" -> {
            context.resources.getColor(R.color.alive)
        }
        "Dead" -> {
            context.resources.getColor(R.color.dead)
        }
        "unknown" -> {
            context.resources.getColor(R.color.unknown)
        }
        else -> {
            context.resources.getColor(R.color.unknown)
        }
    }
}


