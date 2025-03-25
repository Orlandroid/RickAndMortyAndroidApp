package com.rickandmortyorlando.orlando.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TextLink(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Default,
            letterSpacing = 0.25.sp
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun TextLinkPreview(modifier: Modifier = Modifier) {
    TextLink(text = "Android")
}