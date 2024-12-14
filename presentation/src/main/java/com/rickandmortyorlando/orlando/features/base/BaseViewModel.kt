package com.rickandmortyorlando.orlando.features.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import kotlinx.coroutines.*
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException


abstract class BaseViewModel(
    protected val coroutineDispatchers: CoroutineDispatchers,
    val networkHelper: NetworkHelper
) : ViewModel() {

    var job = SupervisorJob()

    inline fun <T> safeApiCall(
        result: MutableLiveData<ApiState<T>>,
        coroutineDispatchers: CoroutineDispatchers,
        crossinline apiToCall: suspend () -> Unit,
    ) {
        viewModelScope.launch(coroutineDispatchers.io + job) {
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
                    Timber.tag("ApiCalls").e(e.cause, "Call error: ${e.localizedMessage}")
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
        job.cancel()
    }


}



