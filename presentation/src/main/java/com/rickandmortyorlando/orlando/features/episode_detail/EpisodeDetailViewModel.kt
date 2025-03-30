package com.rickandmortyorlando.orlando.features.episode_detail

import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.model.character.toCharacter
import com.example.data.model.episode.toEpisode
import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import com.rickandmortyorlando.orlando.state.BaseViewState
import com.rickandmortyorlando.orlando.utils.getListOfNumbersFromUrlWithPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
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
    coroutineDispatchers: CoroutineDispatchers,
    private val repository: Repository,
    networkHelper: NetworkHelper
) : BaseViewModel(
    coroutineDispatchers = coroutineDispatchers,
    networkHelper = networkHelper
) {

    private val _state = MutableStateFlow<BaseViewState<EpisodeDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()


    //Todo handle error becuase we can have errors for one or another service
    //Todo This operations should be move to one repository
    //Todo this logic has to be move to the use case in the future
    //Todo getListOfIdsOfCharacters has to move inside of the use case

    fun getEpisodeInfo(
        episodeId: String
    ) = viewModelScope.launch {
        try {
            val episodeResponse = repository.getManyEpisodes(episodeId)
            val characters =
                repository.getManyCharacters(getListOfIdsOfCharacters(episodeResponse[0].characters))
            _state.value =
                BaseViewState.Content(
                    EpisodeDetailUiState(
                        episode = episodeResponse[0].toEpisode(),
                        characters = characters.map { it.toCharacter() }
                    )
                )
        } catch (e: Exception) {
            _state.value = BaseViewState.Error(message = e.message.orEmpty())
        }
    }

    private fun getListOfIdsOfCharacters(idsInUrl: List<String>): String {
        return getListOfNumbersFromUrlWithPrefix(
            idsInUrl,
            "character"
        )
    }

}