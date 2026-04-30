package com.rickandmortyorlando.orlando.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.repository.CharacterRepository
import com.rickandmortyorlando.orlando.features.characters.CharacterEffects.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CharacterEvents {
    data class OnCharacterClicked(val characterId: Int) : CharacterEvents()
    object OnSearchClicked : CharacterEvents()
}

sealed class CharacterEffects {
    data class NavigateToCharacterDetail(val characterId: Int) : CharacterEffects()
    object NavigateToSearchScreen : CharacterEffects()
}

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val characters = characterRepository.getCharacters().cachedIn(viewModelScope)

    private val _effects = Channel<CharacterEffects>()

    val effects = _effects.receiveAsFlow()

    fun handleEvent(event: CharacterEvents) {
        when (event) {
            is CharacterEvents.OnCharacterClicked -> {
                viewModelScope.launch {
                    _effects.send(NavigateToCharacterDetail(event.characterId))
                }
            }

            CharacterEvents.OnSearchClicked -> {
                viewModelScope.launch {
                    _effects.send(NavigateToSearchScreen)
                }
            }
        }
    }

}