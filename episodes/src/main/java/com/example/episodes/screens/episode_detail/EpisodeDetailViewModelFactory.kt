package com.example.episodes.screens.episode_detail

import dagger.assisted.AssistedFactory

@AssistedFactory
interface EpisodeDetailViewModelFactory {
    fun create(episodeId: Int): EpisodeDetailViewModel
}