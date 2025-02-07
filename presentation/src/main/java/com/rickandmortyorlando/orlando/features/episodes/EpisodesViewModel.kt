package com.rickandmortyorlando.orlando.features.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.Repository
import com.example.data.api.RickAndMortyService
import com.example.data.model.episode.EpisodeData
import com.example.data.pagination.EpisodesPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: Repository,
    private val rickAndMortyService: RickAndMortyService,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
) : BaseViewModel(coroutineDispatcher, networkHelper) {


    private val _manyEpisodesResponse = MutableLiveData<ApiState<List<EpisodeData>>>()
    val manyEpisodesResponse: LiveData<ApiState<List<EpisodeData>>>
        get() = _manyEpisodesResponse

    var comesFromEpisodesMainMenu: Boolean = false

    private lateinit var episodesPagingSource: EpisodesPagingSource

    val getEpisodesPagingSource: Flow<PagingData<EpisodeData>> = Pager(
        config = getPagingConfig(),
        pagingSourceFactory = {
            episodesPagingSource = EpisodesPagingSource(service = rickAndMortyService)
            episodesPagingSource
        }
    ).flow.cachedIn(viewModelScope)


    fun refreshEpisodesPagingSource() = episodesPagingSource.invalidate()


    fun getManyEpisodesResponse(idsEpisodes: String) {
        viewModelScope.launch {
            safeApiCall(_manyEpisodesResponse, coroutineDispatchers) {
                val response = repository.getManyEpisodes(idsEpisodes)
                withContext(Dispatchers.Main) {
                    _manyEpisodesResponse.value = ApiState.Success(response)
                }
            }
        }
    }

}