package com.rickandmortyorlando.orlando.features.episode_detail

import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.model.character.CharacterData
import com.example.data.model.episode.toEpisode
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import com.rickandmortyorlando.orlando.utils.getListOfNumbersFromUrlWithPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class EpisodeDetailViewState {
    data object Loading : EpisodeDetailViewState()
    data class Success(val episode: Episode, val characters: List<CharacterData>) : EpisodeDetailViewState()
    data class Error(val message: String) : EpisodeDetailViewState()
}

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    private val repository: Repository,
    networkHelper: NetworkHelper
) : BaseViewModel(
    coroutineDispatchers = coroutineDispatchers,
    networkHelper = networkHelper
) {

    private var idsOfCharacters = ""

    private val _state = MutableStateFlow<EpisodeDetailViewState>(EpisodeDetailViewState.Loading)
    val state = _state.asStateFlow()


    //Todo handle error becuase we can have errors for one or another service
    //Todo This operations should be move to one repository
    //Todo this logic has to be move to the use case in the future

    fun getEpisodeInfo(
        episodeId: String
    ) = viewModelScope.launch {
        try {
            val episodeResponse = repository.getManyEpisodes(episodeId)
            val characters =
                repository.getManyCharacters(getListOfIdsOfCharacters(episodeResponse[0].characters))
            _state.value =
                EpisodeDetailViewState.Success(
                    episode = episodeResponse[0].toEpisode(),
                    characters = characters
                )
        } catch (e: Exception) {
            _state.value = EpisodeDetailViewState.Error(message = e.message.orEmpty())
        }
    }

    private fun getListOfIdsOfCharacters(idsInUrl: List<String>): String {
        return getListOfNumbersFromUrlWithPrefix(
            idsInUrl,
            "character"
        )
    }

}