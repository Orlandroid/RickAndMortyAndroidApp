package com.rickandmortyorlando.orlando.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rickandmortyorlando.orlando.theme.StatusBarColor

@Composable
fun CustomSearchBar(
    value: String,
    placeholder: String,
    navigateUp: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val requester = remember { FocusRequester() }
    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(StatusBarColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            OutlinedTextField(
                value = value,
                onValueChange = { name ->
                    onValueChange(name)
                },
                placeholder = {
                    Text(text = placeholder, color = Color.White)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = StatusBarColor,
                    disabledContainerColor = StatusBarColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = requester
                    ),
                trailingIcon = {
                    if (value.isNotBlank()) {
                        IconButton(
                            onClick = {
                                onValueChange("")
                            }
                        ) {
                            Icon(
                                tint = Color.White,
                                imageVector = Icons.Default.Clear,
                                contentDescription = "clear Search",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CustomSearchBarPreview(modifier: Modifier = Modifier) {
    CustomSearchBar(value = "Andorid", placeholder = "Search", navigateUp = {}, onValueChange = {})
}
