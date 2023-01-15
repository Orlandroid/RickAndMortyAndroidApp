package com.example.paggingexample.ui.episodes

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paggingexample.data.Repository
import com.example.paggingexample.data.models.remote.episode.EpisodeResponse
import com.example.paggingexample.data.models.remote.episode.Episode
import com.example.paggingexample.data.state.ApiState
import com.example.paggingexample.ui.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _episodeResponse = MutableLiveData<ApiState<EpisodeResponse>>()
    val episodeResponse: LiveData<ApiState<EpisodeResponse>>
        get() = _episodeResponse

    private val _manyEpisodesResponse = MutableLiveData<ApiState<List<Episode>>>()
    val manyEpisodesResponse: LiveData<ApiState<List<Episode>>>
        get() = _manyEpisodesResponse

    @SuppressLint("NullSafeMutableLiveData")
    fun getEpisodes(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _episodeResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _episodeResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getEpisodes(page)
                withContext(Dispatchers.Main) {
                    _episodeResponse.value = ApiState.Success(response)
                    _episodeResponse.value = null
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _episodeResponse.value = ApiState.Error(e)
                }
            }
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun getManyEpisodesResponse(idsEpisodes:String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _manyEpisodesResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _manyEpisodesResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getManyEpisodes(idsEpisodes)
                withContext(Dispatchers.Main) {
                    _manyEpisodesResponse.value = ApiState.Success(response)
                    _manyEpisodesResponse.value = null
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _manyEpisodesResponse.value = ApiState.Error(e)
                }
            }
        }
    }


}