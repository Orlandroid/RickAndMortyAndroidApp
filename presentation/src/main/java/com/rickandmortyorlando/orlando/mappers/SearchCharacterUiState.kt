package com.rickandmortyorlando.orlando.mappers

import com.example.domain.models.characters.SearchCharacter
import com.rickandmortyorlando.orlando.features.search.SearchCharacterUiState


fun SearchCharacterUiState.toSearchCharacter() =
    SearchCharacter(name = name, status = status, species = species, gender = gender, type = type)