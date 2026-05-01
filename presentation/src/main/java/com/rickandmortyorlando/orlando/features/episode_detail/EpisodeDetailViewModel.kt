package com.rickandmortyorlando.orlando.features.episode_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.example.domain.models.episodes.EpisodeImage
import com.example.domain.state.getData
import com.example.domain.state.getMessage
import com.example.domain.state.isError
import com.example.domain.usecases.GetEpisodeDetailUseCase
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed class EpisodeDetailEvents {
    data class OnCharacterClicked(val episodeId: Int) : EpisodeDetailEvents()
    data class OnWatchClicked(val episodeName: String) : EpisodeDetailEvents()
}

sealed class EpisodeDetailEffects {
    data class NavigateToCharacterDetail(val characterId: Int) : EpisodeDetailEffects()
    data class OpenYoutube(val episodeName: String) : EpisodeDetailEffects()
}

data class EpisodeDetailUiState(
    val episode: Episode,
    val characters: List<Character>,
    val episodeImage: EpisodeImage? = null
)

@HiltViewModel(assistedFactory = EpisodeDetailViewModelFactory::class)
class EpisodeDetailViewModel @AssistedInject constructor(
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getEpisodeDetailUseCase: GetEpisodeDetailUseCase,
    @Assisted private val episodeId: Int
) : ViewModel() {

    private val _state =
        MutableStateFlow<BaseViewState<EpisodeDetailUiState>>(BaseViewState.Loading)
    val state = _state.onStart {
        getEpisodeDetail(episodeId = episodeId.toString())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = BaseViewState.Loading
    )

    private val _effects = Channel<EpisodeDetailEffects>()

    val effects = _effects.receiveAsFlow()


    fun getEpisodeDetail(episodeId: String) = viewModelScope.launch(ioDispatcher) {
        val episodeDetail = getEpisodeDetailUseCase.invoke(episodeId = episodeId)
        if (episodeDetail.isError()) {
            _state.value = BaseViewState.Error(message = episodeDetail.getMessage())
            return@launch
        }
        val episode = episodeDetail.getData()
        _state.value = BaseViewState.Content(
            EpisodeDetailUiState(
                episode = episode.episode,
                characters = episode.characters,
                episodeImage = episode.episodeImage
            )
        )
    }

    fun onEvents(event: EpisodeDetailEvents) {
        when (event) {

            is EpisodeDetailEvents.OnCharacterClicked -> {
                viewModelScope.launch {
                    _effects.send(EpisodeDetailEffects.NavigateToCharacterDetail(characterId = event.episodeId))
                }
            }

            is EpisodeDetailEvents.OnWatchClicked -> {
                viewModelScope.launch {
                    _effects.send(EpisodeDetailEffects.OpenYoutube(episodeName = event.episodeName))
                }

            }
        }
    }


}