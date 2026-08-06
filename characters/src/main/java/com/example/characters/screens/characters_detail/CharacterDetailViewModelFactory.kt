package com.example.characters.screens.characters_detail

import dagger.assisted.AssistedFactory

@AssistedFactory
interface CharacterDetailViewModelFactory {
    fun create(characterId: Int): CharacterDetailViewModel
}