package com.example.paggingexample.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paggingexample.data.Repository
import com.example.paggingexample.data.models.CharacterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _characterResponse = MutableLiveData<CharacterResponse>()
    val characterResponse: LiveData<CharacterResponse> get() = _characterResponse


    fun getCharacters(page: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = repository.getCharacters(page)
                withContext(Dispatchers.Main) {
                    _characterResponse.value = response
                }
            } catch (e: Exception) {
            }
        }
    }

}