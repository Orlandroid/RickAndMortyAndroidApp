package com.rickandmortyorlando.orlando.features.filter_dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.characters.SearchCharacter
import com.rickandmortyorlando.orlando.R


@Composable
fun FilterScreen(
    currentFilter: SearchCharacter,
    onSearchClicked: (SearchCharacter) -> Unit
) {
    FilterScreenContent(
        modifier = Modifier,
        searchFilter = onSearchClicked,
        currentFilter = currentFilter
    )
}

@Composable
fun FilterScreenContent(
    currentFilter: SearchCharacter,
    modifier: Modifier = Modifier,
    searchFilter: (SearchCharacter) -> Unit
) {
    var searchInfo = remember { currentFilter }
    Column(
        modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(16.dp))
        InputCharacter(searchInfo = searchInfo)
        Spacer(Modifier.height(16.dp))
        FilterGivenStatus(
            _selected = searchInfo.status,
            changeSelection = {
                searchInfo.status = it
            }
        )
        Spacer(Modifier.height(16.dp))
        InputSpecies(searchInfo = searchInfo)
        Spacer(Modifier.height(16.dp))
        InputType(searchInfo = searchInfo)
        Spacer(Modifier.height(16.dp))
        FilterGivenGender(
            _selected = searchInfo.gender,
            changeSelection = {
                searchInfo.gender = it
            }
        )
        Spacer(Modifier.height(16.dp))
        ResultButton(
            searchFilter = {
                searchFilter.invoke(searchInfo)
            },
            reset = {
                searchInfo = SearchCharacter()
            }
        )
    }
}

@Composable
fun InputCharacter(
    searchInfo: SearchCharacter
) {
    FilterTextField(
        textToSearch = searchInfo.name,
        changeSearch = {
            searchInfo.name = it
        },
        label = "Character"
    )
}

@Composable
fun InputSpecies(
    searchInfo: SearchCharacter
) {
    FilterTextField(
        textToSearch = searchInfo.species,
        changeSearch = {
            searchInfo.species = it
        },
        label = "Species"
    )
}

@Composable
fun InputType(
    searchInfo: SearchCharacter
) {
    FilterTextField(
        textToSearch = searchInfo.type,
        changeSearch = {
            searchInfo.type = it
        },
        label = "Type"
    )
}


@Composable
@Preview(showBackground = true)
fun FilterScreenPreview(modifier: Modifier = Modifier) {
    FilterScreen(
        currentFilter = SearchCharacter(),
        onSearchClicked = {}
    )
}


@Composable
fun FilterTextField(
    textToSearch: String = "",
    changeSearch: (String) -> Unit,
    label: String
) {
    var searchText by remember { mutableStateOf(textToSearch) }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            changeSearch(searchText)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        shape = MaterialTheme.shapes.small,
        trailingIcon = {
            CancelInput(
                searchText = {
                    searchText = ""
                    changeSearch(searchText)
                }
            )
        }
    )
}

// Selecting a status parameter
@Composable
fun FilterGivenStatus(
    _selected: String,
    changeSelection: (String) -> Unit
) {
    val option = listOf("All", "Alive", "Dead", "Unknown")
    val selected by remember { mutableStateOf(if (_selected == "") "All" else _selected) }
    var state by remember { mutableIntStateOf(option.indexOfFirst { it == selected }) }

    TabRow(
        selectedTabIndex = state,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        option.forEachIndexed { index, status ->
            Tab(
                selected = state == index,
                onClick = {
                    state = index
                    changeSelection(if (status == "All") "" else status)
                },
                text = {
                    Text(
                        text = status,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                modifier = Modifier.clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

// Selecting the gender parameter
@Composable
fun FilterGivenGender(
    _selected: String,
    changeSelection: (String) -> Unit
) {
    val option = listOf("All", "Female", "Male", "Genderless", "Unknown")
    val selected by remember { mutableStateOf(if (_selected == "") "All" else _selected) }
    var state by remember { mutableIntStateOf(option.indexOfFirst { it == selected }) }

    ScrollableTabRow(
        selectedTabIndex = state,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        option.forEachIndexed { index, status ->
            Tab(
                selected = state == index,
                onClick = {
                    state = index
                    changeSelection(if (status == "All") "" else status)
                },
                text = {
                    Text(
                        text = status,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                modifier = Modifier.clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

@Composable
fun CancelInput(
    searchText: () -> Unit
) {
    IconButton(
        onClick = searchText

    ) {
        Icon(
            painter = painterResource(id = R.drawable.cancel),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ResultButton(
    searchFilter: () -> Unit,
    reset: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = searchFilter,
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {
            Text(text = "Search")
        }
        Button(
            onClick = {
                reset()
                searchFilter()
            },
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {
            Text(text = "Reset")
        }
    }
}
