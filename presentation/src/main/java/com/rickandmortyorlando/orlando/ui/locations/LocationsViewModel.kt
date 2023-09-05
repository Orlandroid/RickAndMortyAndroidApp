package com.rickandmortyorlando.orlando.ui.locations

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.Repository
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.CharactersPagingSource
import com.example.data.pagination.LocationPagingSource
import com.example.domain.models.remote.character.Character
import com.example.domain.state.ApiState
import com.example.domain.models.remote.location.LocationsResponse
import com.example.domain.models.remote.location.SingleLocation
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.ui.base.BaseViewModel
import com.rickandmortyorlando.orlando.ui.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val repository: Repository,
    private val rickAndMortyService: RickAndMortyService,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers
) : BaseViewModel(networkHelper = networkHelper, coroutineDispatchers = coroutineDispatcher) {

    private val _locationResponse = MutableLiveData<ApiState<LocationsResponse>>()
    val locationResponse: LiveData<ApiState<LocationsResponse>>
        get() = _locationResponse

    private val _singleLocationResponse = MutableLiveData<ApiState<SingleLocation>>()
    val singleLocationResponse: LiveData<ApiState<SingleLocation>> get() = _singleLocationResponse


    fun getSingleLocation(locationId: Int) {
        viewModelScope.launch {
            safeApiCall(_singleLocationResponse, coroutineDispatchers) {
                val response = repository.getSingleLocation(locationId)
                withContext(Dispatchers.Main) {
                    _singleLocationResponse.value = ApiState.Success(response)
                }
            }
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun getLocations(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _locationResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _locationResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getLocations(page)
                withContext(Dispatchers.Main) {
                    _locationResponse.value = ApiState.Success(response)
                    _locationResponse.value = null
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _locationResponse.value = ApiState.Error(e)
                }
            }
        }
    }

    private lateinit var locationPagingSource: LocationPagingSource

    fun getLocationsPagingSource(): Flow<PagingData<SingleLocation>> {
        return Pager(
            config = PagingConfig(
                pageSize = Repository.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = Repository.PRE_FETCH_DISTANCE
            ),
            pagingSourceFactory = {
                locationPagingSource = LocationPagingSource(service = rickAndMortyService)
                locationPagingSource
            }
        ).flow
    }

    fun refreshLocationsPagingSource() = locationPagingSource.invalidate()


}