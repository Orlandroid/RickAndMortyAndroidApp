package com.rickandmortyorlando.orlando.features.episodes

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.EpisodesPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.di.CoroutineDispatchers
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val rickAndMortyService: RickAndMortyService,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
) : BaseViewModel(coroutineDispatcher, networkHelper) {


    var comesFromEpisodesMainMenu: Boolean = false

    private lateinit var episodesPagingSource: EpisodesPagingSource

    val getEpisodesPagingSource: Flow<PagingData<Episode>> = Pager(
        config = getPagingConfig(),
        pagingSourceFactory = {
            episodesPagingSource = EpisodesPagingSource(service = rickAndMortyService)
            episodesPagingSource
        }
    ).flow.cachedIn(viewModelScope)


    fun refreshEpisodesPagingSource() = episodesPagingSource.invalidate()



}