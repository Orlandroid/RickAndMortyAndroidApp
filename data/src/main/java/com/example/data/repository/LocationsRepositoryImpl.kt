package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.data.api.RickAndMortyService
import com.example.data.model.location.toLocation
import com.example.data.pagination.LocationPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.location.Location
import com.example.domain.repository.LocationRepository
import com.example.domain.state.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.runCatching

class LocationsRepositoryImpl @Inject constructor(private val api: RickAndMortyService) :
    LocationRepository {

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

    override suspend fun getLocation(idLocation: Int): ApiResult<Location> {
        return runCatching { api.getSingleLocation(idLocation).toLocation() }
            .fold(
                onSuccess = { ApiResult.Success(it) },
                onFailure = { ApiResult.Error(it.message.orEmpty()) })
    }

}