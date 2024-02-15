package com.rickandmortyorlando.orlando.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.ui.main.NetworkHelper
import kotlinx.coroutines.*
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException


abstract class BaseViewModel(
    protected val coroutineDispatchers: CoroutineDispatchers,
    val networkHelper: NetworkHelper
) : ViewModel() {

    enum class ErrorType {
        NETWORK,
        TIMEOUT,
        UNKNOWN
    }

    var job = SupervisorJob()

    suspend inline fun <T> safeApiCall(
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
                    Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
                    when (e) {
                        is HttpException -> {
                            val errorBody = e.response()?.errorBody()
                            val errorCode = e.response()?.code()
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



