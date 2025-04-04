package com.rickandmortyorlando.orlando.features.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.CoroutineDispatchers
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException


abstract class BaseViewModel(
    protected val coroutineDispatchers: CoroutineDispatchers,
    val networkHelper: NetworkHelper
) : ViewModel() {

    private var job: Job? = null

    inline fun <T> safeApiCall(
        result: MutableLiveData<ApiState<T>>,
        coroutineDispatchers: CoroutineDispatchers,
        crossinline apiToCall: suspend () -> Unit,
    ) {
        viewModelScope.launch(coroutineDispatchers.io) {
            try {
                withContext(coroutineDispatchers.main) {
                    result.value = ApiState.Loading()
                }
                if (!networkHelper.isNetworkConnected()) {
                    result.value = ApiState.ErrorNetwork()
                    return@launch
                }
                apiToCall()
                withContext(coroutineDispatchers.main) {
                    result.value = null
                }
            } catch (e: Exception) {
                withContext(coroutineDispatchers.main) {
                    e.printStackTrace()
                    when (e) {
                        is HttpException -> {
                            result.value = ApiState.Error(e)
                        }

                        is SocketTimeoutException -> result.value =
                            ApiState.Error(e)

                        is IOException -> result.value = ApiState.Error(e)
                        else -> result.value = ApiState.Error(e)
                    }
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}



