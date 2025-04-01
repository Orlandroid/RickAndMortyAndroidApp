package com.rickandmortyorlando.orlando.features.location_detail

import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.model.character.toCharacter
import com.example.data.model.location.SingleLocation
import com.example.di.CoroutineDispatchers
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import com.rickandmortyorlando.orlando.state.BaseViewState
import com.rickandmortyorlando.orlando.utils.getListOfNumbersFromUrlWithPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LocationDetailUiState(
    val location: SingleLocation,
    val characters: List<Character>
)

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    private val repository: Repository,
    networkHelper: NetworkHelper
) : BaseViewModel(coroutineDispatchers = coroutineDispatchers, networkHelper = networkHelper) {

    private var idsOfCharacters = ""

    private val _state =
        MutableStateFlow<BaseViewState<LocationDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()


    //Todo handle error becuase we can have errors for one or another service

    fun getLocationInfo(locationId: Int) = viewModelScope.launch {
        try {
            val locationResponse = repository.getSingleLocation(locationId)
            idsOfCharacters = getListOfIdsOfCharacters(locationResponse.residents)
            val characterResponse = if (isSingleCharacter()) {
                listOf(repository.getCharacter(idsOfCharacters)).map { it.toCharacter() }
            } else {
                repository.getManyCharacters(idsOfCharacters).map { it.toCharacter() }
            }
            _state.value =
                BaseViewState.Content(
                    LocationDetailUiState(
                        location = locationResponse,
                        characters = characterResponse
                    )
                )
        } catch (e: Exception) {
            _state.value = BaseViewState.Error(message = e.message.orEmpty())
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