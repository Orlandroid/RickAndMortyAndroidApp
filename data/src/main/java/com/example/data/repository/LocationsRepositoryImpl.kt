package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.LocationPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.location.Location
import com.example.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(private val api: RickAndMortyService) : LocationsRepository {

    private lateinit var locationPagingSource: LocationPagingSource


    override fun getLocations(): Flow<PagingData<Location>> {
        return Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                locationPagingSource = LocationPagingSource(service = api)
                locationPagingSource
            }
        ).flow
    }

}