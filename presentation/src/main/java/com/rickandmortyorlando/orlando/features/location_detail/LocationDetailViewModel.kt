package com.rickandmortyorlando.orlando.features.location_detail

import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.location.SingleLocation
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import com.rickandmortyorlando.orlando.utils.getListOfNumbersFromUrlWithPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LocationState {
    data object Loading : LocationState()
    data class Success(val location: SingleLocation, val character: List<Character>) :
        LocationState()
    data class Error(val message: String) : LocationState()
}

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    private val repository: Repository,
    networkHelper: NetworkHelper
) : BaseViewModel(coroutineDispatchers = coroutineDispatchers, networkHelper = networkHelper) {

    private var idsOfCharacters = ""

    private val _state = MutableStateFlow<LocationState>(LocationState.Loading)
    val state = _state.asStateFlow()


    //Todo handle error becuase we can have errors for one or another service

    fun getLocationInfo(locationId: Int) = viewModelScope.launch {
        try {
            val locationResponse = repository.getSingleLocation(locationId)
            idsOfCharacters = getListOfIdsOfCharacters(locationResponse.residents)
            val characterResponse = if (isSingleCharacter()) {
                listOf(repository.getCharacter(idsOfCharacters))
            } else {
                repository.getManyCharacters(idsOfCharacters)
            }
            _state.value =
                LocationState.Success(location = locationResponse, character = characterResponse)
        } catch (e: Exception) {
            _state.value = LocationState.Error(message = e.message.orEmpty())
        }
    }

    private fun getListOfIdsOfCharacters(idsInUrl: List<String>): String {
        return getListOfNumbersFromUrlWithPrefix(
            idsInUrl,
            "character"
        )
    }

    private fun isSingleCharacter() = !idsOfCharacters.contains(",")


}