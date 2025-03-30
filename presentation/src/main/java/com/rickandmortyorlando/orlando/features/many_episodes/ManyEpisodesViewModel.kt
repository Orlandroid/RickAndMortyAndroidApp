package com.rickandmortyorlando.orlando.features.many_episodes

import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.model.episode.toEpisode
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ManyEpisodesViewModel @Inject constructor(
    private val repository: Repository,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
) : BaseViewModel(coroutineDispatcher, networkHelper) {

    private val _state = MutableStateFlow<BaseViewState<List<Episode>>>(BaseViewState.Loading)
    val state = _state.asStateFlow()

    fun getEpisodes(idsEpisodes: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val episodeResponse = repository.getManyEpisodes(idsEpisodes).map { it.toEpisode() }
                _state.value =
                    BaseViewState.Content(
                        result = episodeResponse
                    )
            } catch (e: Exception) {
                _state.value = BaseViewState.Error(message = e.message.orEmpty())
            }
        }
    }


}