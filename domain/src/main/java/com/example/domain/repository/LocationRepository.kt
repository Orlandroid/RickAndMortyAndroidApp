package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocations(): Flow<PagingData<Location>>

    suspend fun getLocation(idLocation:Int): Location
}