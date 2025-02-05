package com.rickandmortyorlando.orlando.features.episode_detail

import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.episode.Episode
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import com.rickandmortyorlando.orlando.utils.getListOfNumbersFromUrlWithPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class EpisodeDetailState {
    data object Loading : EpisodeDetailState()
    data class Success(val episode: Episode, val characters: List<Character>) : EpisodeDetailState()
    data class Error(val message: String) : EpisodeDetailState()
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

    private val _state = MutableStateFlow<EpisodeDetailState>(EpisodeDetailState.Loading)
    val state = _state.asStateFlow()


    //Todo handle error becuase we can have errors for one or another service

    fun getEpisodeInfo(locationId: Int) = viewModelScope.launch {

    }

    private fun getListOfIdsOfCharacters(idsInUrl: List<String>): String {
        return getListOfNumbersFromUrlWithPrefix(
            idsInUrl,
            "character"
        )
    }

    private fun isSingleCharacter() = !idsOfCharacters.contains(",")


}