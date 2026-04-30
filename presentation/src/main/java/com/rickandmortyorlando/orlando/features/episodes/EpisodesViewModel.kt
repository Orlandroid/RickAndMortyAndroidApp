package com.rickandmortyorlando.orlando.features.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.repository.EpisodesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class EpisodesEvents {
    data class OnEpisodeClicked(val episodeId: Int) : EpisodesEvents()
}

sealed class EpisodesEffects {
    data class NavigateToEpisodeDetail(val episodeId: Int) : EpisodesEffects()
}

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    val episodes = episodesRepository.getEpisodes().cachedIn(viewModelScope)

    private val _effects = Channel<EpisodesEffects>()

    val effects = _effects.receiveAsFlow()

    fun onEvents(event: EpisodesEvents) {
        when (event) {
            is EpisodesEvents.OnEpisodeClicked -> {
                viewModelScope.launch {
                    _effects.send(EpisodesEffects.NavigateToEpisodeDetail(event.episodeId))
                }
            }
        }
    }

}