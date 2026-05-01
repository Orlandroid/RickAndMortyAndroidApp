package com.rickandmortyorlando.orlando.features.episode_detail

import dagger.assisted.AssistedFactory

@AssistedFactory
interface EpisodeDetailViewModelFactory {
    fun create(episodeId: Int): EpisodeDetailViewModel
}