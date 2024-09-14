package com.rickandmortyorlando.orlando.ui.characters_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.location.SingleLocation
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.ui.base.BaseViewModel
import com.rickandmortyorlando.orlando.ui.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: Repository,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
) : BaseViewModel(coroutineDispatcher, networkHelper) {

    private val _characterResponse = MutableLiveData<ApiState<Character>>()
    val characterResponse: LiveData<ApiState<Character>>
        get() = _characterResponse

    private val _locationResponse = MutableLiveData<ApiState<SingleLocation>>()
    val locationResponse: LiveData<ApiState<SingleLocation>>
        get() = _locationResponse

    fun getCharacter(id: String) = viewModelScope.launch {
        safeApiCall(_characterResponse, coroutineDispatchers) {
            val response = repository.getCharacter(id)
            withContext(Dispatchers.Main) {
                _characterResponse.value = ApiState.Success(response)
            }
        }
    }


    fun getSingleLocation(id: Int) = viewModelScope.launch {
        safeApiCall(_locationResponse, coroutineDispatchers) {
            val response = repository.getSingleLocation(id)
            withContext(Dispatchers.Main) {
                _locationResponse.value = ApiState.Success(response)
            }
        }
    }


}