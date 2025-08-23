package com.rickandmortyorlando.orlando.features.many_episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.models.episodes.Episode
import com.example.domain.repository.EpisodesRepository
import com.example.domain.state.getData
import com.example.domain.state.getMessage
import com.example.domain.state.isError
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ManyEpisodesEvents {
    data class OnEpisodeClicked(val episodeId: Int) : ManyEpisodesEvents()
}

sealed class ManyEpisodesEffects {
    data class NavigateToEpisodeDetail(val episodeId: Int) : ManyEpisodesEffects()
}

@HiltViewModel
class ManyEpisodesViewModel @Inject constructor(
    private val repository: EpisodesRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _effects = Channel<ManyEpisodesEffects>()

    val effects = _effects.receiveAsFlow()

    private val _state = MutableStateFlow<BaseViewState<List<Episode>>>(BaseViewState.Loading)
    val state = _state.asStateFlow()

    fun onEvents(event: ManyEpisodesEvents) {
        when (event) {
            is ManyEpisodesEvents.OnEpisodeClicked -> {
                viewModelScope.launch {
                    _effects.send(ManyEpisodesEffects.NavigateToEpisodeDetail(event.episodeId))
                }
            }
        }
    }


    fun getEpisodes(idsEpisodes: String) {
        viewModelScope.launch(ioDispatcher) {
            val episodeResponse = repository.getManyEpisodes(idsEpisodes)
            if (episodeResponse.isError()) {
                _state.value = BaseViewState.Error(message = episodeResponse.getMessage())
                return@launch
            }
            _state.value = BaseViewState.Content(result = episodeResponse.getData())
        }
    }


}