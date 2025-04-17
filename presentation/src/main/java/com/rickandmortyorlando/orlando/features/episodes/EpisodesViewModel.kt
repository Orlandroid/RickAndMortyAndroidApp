package com.rickandmortyorlando.orlando.features.episodes

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.di.CoroutineDispatchers
import com.example.domain.repository.EpisodesRepository
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
    private val episodesRepository: EpisodesRepository,
) : BaseViewModel(coroutineDispatcher, networkHelper) {


    var comesFromEpisodesMainMenu: Boolean = false


    val episodes = episodesRepository.getEpisodes().cachedIn(viewModelScope)


}