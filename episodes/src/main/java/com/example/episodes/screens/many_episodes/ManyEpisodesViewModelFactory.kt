package com.example.episodes.screens.many_episodes

import dagger.assisted.AssistedFactory

@AssistedFactory
interface ManyEpisodesViewModelFactory {
    fun create(episodesIds: Int): ManyEpisodesViewModel
}