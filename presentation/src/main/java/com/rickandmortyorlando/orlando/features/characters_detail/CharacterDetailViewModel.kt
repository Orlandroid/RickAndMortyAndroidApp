package com.rickandmortyorlando.orlando.features.characters_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.model.character.toCharacter
import com.example.data.model.location.SingleLocation
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.state.BaseViewState
import com.rickandmortyorlando.orlando.utils.getNumberFromUrWithPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CharacterDetailUiState(
    val location: SingleLocation? = null,
    val characterDetail: Character,
    val characterOfThisLocation: List<Character>? = null
)

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state =
        MutableStateFlow<BaseViewState<CharacterDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()


    fun getCharacterDetailInfo(idCharacter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val character = repository.getCharacter(id = idCharacter).toCharacter()
                var singleLocation: SingleLocation? = null
                var characterOfThisLocation: List<Character>? = null
                if (characterHasLocation(character.urlLocation)) {
                    val locationId = getNumberFromUrWithPrefix(
                        character.originUrl.ifEmpty { character.urlLocation },
                        "location"
                    )
                    singleLocation = repository.getSingleLocation(locationId)
                    characterOfThisLocation =
                        repository.getManyCharacters(getListOfIdsOfCharacters(singleLocation.residents))
                            .map { it.toCharacter() }
                }
                _state.value =
                    BaseViewState.Content(
                        CharacterDetailUiState(
                            location = singleLocation,
                            characterDetail = character,
                            characterOfThisLocation = characterOfThisLocation
                        )
                    )
            } catch (e: Exception) {
                _state.value = BaseViewState.Error(message = e.message.orEmpty())
            }
        }
    }


}