package com.example.paggingexample.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paggingexample.data.Repository
import com.example.paggingexample.data.models.episode.Episode
import com.example.paggingexample.ui.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: Repository, private val networkHelper: NetworkHelper
) : ViewModel() {


    fun getEpisodes(): Flow<PagingData<Episode>> {
        return repository.getEpisodes().cachedIn(viewModelScope)
    }

}