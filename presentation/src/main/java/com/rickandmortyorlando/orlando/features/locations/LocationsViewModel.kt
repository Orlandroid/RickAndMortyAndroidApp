package com.rickandmortyorlando.orlando.features.locations

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.Repository
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.LocationPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.location.Location
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val repository: Repository,
    private val rickAndMortyService: RickAndMortyService,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers
) : BaseViewModel(networkHelper = networkHelper, coroutineDispatchers = coroutineDispatcher) {



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