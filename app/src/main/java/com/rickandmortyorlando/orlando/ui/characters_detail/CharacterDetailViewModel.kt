package com.rickandmortyorlando.orlando.ui.characters_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmortyorlando.orlando.data.Repository
import com.rickandmortyorlando.orlando.data.models.remote.character.Character
import com.rickandmortyorlando.orlando.data.models.remote.location.SingleLocation
import com.rickandmortyorlando.orlando.data.state.ApiState
import com.rickandmortyorlando.orlando.ui.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _characterResponse = MutableLiveData<ApiState<Character>>()
    val characterResponse: LiveData<ApiState<Character>>
        get() = _characterResponse

    private val _locationResponse = MutableLiveData<ApiState<SingleLocation>>()
    val locationResponse: LiveData<ApiState<SingleLocation>>
        get() = _locationResponse

    fun getCharacter(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _characterResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _characterResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getCharacter(id)
                withContext(Dispatchers.Main) {
                    _characterResponse.value = ApiState.Success(response)
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _characterResponse.value = ApiState.Error(e)
                }
            }
        }
    }

    fun getSingleLocation(id: Int) {
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
                val response = repository.getSingleLocation(id)
                withContext(Dispatchers.Main) {
                    _locationResponse.value = ApiState.Success(response)
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _locationResponse.value = ApiState.Error(e)
                }
            }
        }
    }

}