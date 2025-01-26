package com.rickandmortyorlando.orlando.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemKeyValue(
    textSize: TextUnit = 24.sp,
    separator: String = ":",
    key: String,
    value: String
) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        Text(
            style = TextStyle(fontWeight = FontWeight.Bold),
            text = key.plus(separator),
            fontSize = textSize,
        )
        Spacer(
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = value,
            fontSize = textSize,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ItemKeyValuePreview() {
    ItemKeyValue(key = "Status", value = "Alive")
}