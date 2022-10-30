package com.example.paggingexample.ui.characters

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paggingexample.data.Repository
import com.example.paggingexample.data.models.character.CharacterResponse
import com.example.paggingexample.data.state.ApiState
import com.example.paggingexample.ui.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _myCharacterResponse = MutableLiveData<ApiState<CharacterResponse>>()
    val myCharacterResponse: LiveData<ApiState<CharacterResponse>>
        get() = _myCharacterResponse

    @SuppressLint("NullSafeMutableLiveData")
    fun getCharacters(page: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(4000)
            withContext(Dispatchers.Main) {
                _myCharacterResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _myCharacterResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getCharacters(page)
                withContext(Dispatchers.Main) {
                    _myCharacterResponse.value = ApiState.Success(response)
                    _myCharacterResponse.value = null
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _myCharacterResponse.value = ApiState.Error(e)
                }
            }
        }
    }

}