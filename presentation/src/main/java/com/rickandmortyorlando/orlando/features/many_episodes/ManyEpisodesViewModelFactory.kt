package com.rickandmortyorlando.orlando.features.many_episodes

import dagger.assisted.AssistedFactory

@AssistedFactory
interface ManyEpisodesViewModelFactory {
    fun create(episodesIds: Int): ManyEpisodesViewModel
}