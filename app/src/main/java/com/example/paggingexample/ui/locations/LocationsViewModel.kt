package com.example.paggingexample.ui.locations

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paggingexample.data.Repository
import com.example.paggingexample.data.models.remote.location.LocationsResponse
import com.example.paggingexample.data.models.remote.location.SingleLocation
import com.example.paggingexample.data.state.ApiState
import com.example.paggingexample.di.CoroutineDispatchers
import com.example.paggingexample.ui.base.BaseViewModel
import com.example.paggingexample.ui.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val repository: Repository,
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
                val response = repository.getLocations(page.toString())
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


}