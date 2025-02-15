package com.rickandmortyorlando.orlando.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun shimmerBrush(
    showShimmer: Boolean = true,
    targetValue: Float = 1000f
): Brush {
    val color = Color(0xFF535454)
    if (showShimmer) {
        val shimmerColors =
            listOf(
                color.copy(alpha = 0.6f),
                color.copy(alpha = 0.2f),
                color.copy(alpha = 0.6f),
            )
        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation =
            transition.animateFloat(
                initialValue = 0f,
                targetValue = targetValue,
                animationSpec =
                infiniteRepeatable(
                    animation = tween(800, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = ""
            )
        return Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    }
    return Brush.linearGradient(
        colors = listOf(Color.Transparent, Color.Transparent),
        start = Offset.Zero,
        end = Offset.Zero
    )
}