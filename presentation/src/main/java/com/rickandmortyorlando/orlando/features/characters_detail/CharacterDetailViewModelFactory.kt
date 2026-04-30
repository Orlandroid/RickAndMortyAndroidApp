package com.rickandmortyorlando.orlando.features.characters_detail

import dagger.assisted.AssistedFactory

@AssistedFactory
interface CharacterDetailViewModelFactory {
    fun create(characterId: Int): CharacterDetailViewModel
}