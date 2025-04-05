package com.rickandmortyorlando.orlando.features.episode_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
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
    val characters: List<Character>
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
        runCatching {
            val episodeDetail = getEpisodeDetailUseCase.invoke(episodeId = episodeId)
            _state.value = BaseViewState.Content(
                EpisodeDetailUiState(
                    episode = episodeDetail.episode,
                    characters = episodeDetail.characters
                )
            )
        }.onFailure {
            println(it.message)
            _state.value = BaseViewState.Error(message = it.message.orEmpty())
        }
    }


}