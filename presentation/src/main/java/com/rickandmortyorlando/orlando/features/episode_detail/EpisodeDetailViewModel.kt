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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class EpisodeDetailUiState(
    val episode: Episode,
    val characters: List<Character>,
    val episodeImage: EpisodeImage? = null
)

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getEpisodeDetailUseCase: GetEpisodeDetailUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<BaseViewState<EpisodeDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()


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


}