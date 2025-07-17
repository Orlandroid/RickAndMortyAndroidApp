package com.rickandmortyorlando.orlando.features.many_episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.episodes.Episode
import com.example.domain.repository.EpisodesRepository
import com.example.domain.state.getData
import com.example.domain.state.getMessage
import com.example.domain.state.isError
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ManyEpisodesViewModel @Inject constructor(
    private val repository: EpisodesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<BaseViewState<List<Episode>>>(BaseViewState.Loading)
    val state = _state.asStateFlow()

    fun getEpisodes(idsEpisodes: String) {
        viewModelScope.launch {
            val episodeResponse = repository.getManyEpisodes(idsEpisodes)
            if (episodeResponse.isError()) {
                _state.value = BaseViewState.Error(message = episodeResponse.getMessage())
                return@launch
            }
            _state.value =
                BaseViewState.Content(
                    result = episodeResponse.getData()
                )
        }
    }


}