package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {

    fun getLocations(): Flow<PagingData<Location>>
}