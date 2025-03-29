package com.rickandmortyorlando.orlando.features.many_episodes

import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.model.episode.toEpisode
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ManyEpisodesViewState {
    data object Loading : ManyEpisodesViewState()
    data class Content(val episodes: List<Episode>) : ManyEpisodesViewState()
    data class Error(val message: String) : ManyEpisodesViewState()
}

@HiltViewModel
class ManyEpisodesViewModel @Inject constructor(
    private val repository: Repository,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
) : BaseViewModel(coroutineDispatcher, networkHelper) {

    private val _state = MutableStateFlow<ManyEpisodesViewState>(ManyEpisodesViewState.Loading)
    val state = _state.asStateFlow()

    fun getEpisodes(idsEpisodes: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val episodeResponse = repository.getManyEpisodes(idsEpisodes).map { it.toEpisode() }
                _state.value =
                    ManyEpisodesViewState.Content(
                        episodes = episodeResponse
                    )
            } catch (e: Exception) {
                _state.value = ManyEpisodesViewState.Error(message = e.message.orEmpty())
            }
        }
    }


}