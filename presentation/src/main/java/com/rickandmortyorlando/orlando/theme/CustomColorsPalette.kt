package com.rickandmortyorlando.orlando.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val background: Color = Color.Unspecified,
    val alwaysWhite: Color = Color.Unspecified,
    val statusBarColor: Color = Color.Unspecified,
    val white: Color = Color.Unspecified,
    val black: Color = Color.Unspecified
)

val LightBackground = Color(color = 0xFFd3d3d3)
val LightAlwaysWhite = Color(color = 0xFFFFFFFF)
val LightStatusBarColor = Color(color = 0XFF6200EE)
val LightBlack = Color(color = 0xFF000000)
val LightWhite = Color(color = 0XFFFFFFFF)

val LightCustomColorsPalette = CustomColorsPalette(
    background = LightBackground,
    alwaysWhite = LightAlwaysWhite,
    statusBarColor = LightStatusBarColor,
    white = LightWhite,
    black = LightBlack
)

val DarkBackground = Color(color = 0xFF424949)
val DarkAlwaysWhite = Color(color = 0xFFFFFFFF)
val DarkStatusBarColor = Color(color = 0xFF34495e)
val DarkBlack = Color(color = 0XFFFFFFFF)
val DarkWhite = Color(color = 0xFF000000)


val DarkCustomColorsPalette = CustomColorsPalette(
    background = DarkBackground,
    alwaysWhite = DarkAlwaysWhite,
    statusBarColor = DarkStatusBarColor,
    white = DarkWhite,
    black = DarkBlack
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }