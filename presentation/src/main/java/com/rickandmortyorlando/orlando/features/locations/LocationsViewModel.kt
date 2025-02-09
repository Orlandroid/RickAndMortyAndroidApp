package com.rickandmortyorlando.orlando.features.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.Repository
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.LocationPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.data.model.location.LocationsResponse
import com.example.data.model.location.SingleLocation
import com.example.domain.models.location.Location
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


    fun getSingleLocation(locationId: Int) = viewModelScope.launch {
        safeApiCall(_singleLocationResponse, coroutineDispatchers) {
            val response = repository.getSingleLocation(locationId)
            withContext(Dispatchers.Main) {
                _singleLocationResponse.value = ApiState.Success(response)
            }
        }
    }


    private lateinit var locationPagingSource: LocationPagingSource

    val getLocationsPagingSource: Flow<PagingData<Location>> = Pager(
        config = getPagingConfig(),
        pagingSourceFactory = {
            locationPagingSource = LocationPagingSource(service = rickAndMortyService)
            locationPagingSource
        }
    ).flow.cachedIn(viewModelScope)


    fun refreshLocationsPagingSource() = locationPagingSource.invalidate()


}