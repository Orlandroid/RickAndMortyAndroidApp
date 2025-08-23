package com.rickandmortyorlando.orlando.features.characters_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.state.getData
import com.example.domain.usecases.GetCharacterDetailUseCase
import com.rickandmortyorlando.orlando.features.characters_detail.CharacterDetailEffects.*
import com.rickandmortyorlando.orlando.state.BaseViewState
import com.rickandmortyorlando.orlando.state.asContentOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CharacterDetailUiState(
    val location: Location? = null,
    val characterDetail: Character? = null,
    val characterOfThisLocation: List<Character>? = null
)

fun GetCharacterDetailUseCase.CharacterDetail.toCharacterDetail() = CharacterDetailUiState(
    location = location,
    characterDetail = characterDetail,
    characterOfThisLocation = charactersOfThisLocation
)

sealed class CharacterDetailEvents {
    data object OnClickOnNumberOfEpisodes : CharacterDetailEvents()
    data class OnCharacterClicked(val characterId: Int) : CharacterDetailEvents()
}

sealed class CharacterDetailEffects {
    data class NavigateToManyEpisodesScreen(val idsOfEpisodes: String) : CharacterDetailEffects()
    data class NavigateToCharacterDetail(val characterId: Int) : CharacterDetailEffects()
}

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val characterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {


    private val _effects = Channel<CharacterDetailEffects>()

    val effects = _effects.receiveAsFlow()

    private val _state =
        MutableStateFlow<BaseViewState<CharacterDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()

    private var idsOfEpisodes: String? = null


    fun getCharacterDetailInfo(idCharacter: Int) = viewModelScope.launch(ioDispatcher) {
        runCatching {
            val characterDetail = characterDetailUseCase.invoke(idCharacter).getData()
            idsOfEpisodes = characterDetail.idsOfEpisodes
            _state.value = BaseViewState.Content(characterDetail.toCharacterDetail())
        }.onFailure {
            _state.value = BaseViewState.Error(message = it.message.orEmpty())
            print(it.message)
        }
    }

    fun handleEvent(event: CharacterDetailEvents) {
        when (event) {
            is CharacterDetailEvents.OnClickOnNumberOfEpisodes -> {
                viewModelScope.launch {
                    idsOfEpisodes?.let {
                        _effects.send(NavigateToManyEpisodesScreen(it))
                    }
                }
            }

            is CharacterDetailEvents.OnCharacterClicked -> {
                val idCharacter = _state.value.asContentOrNull()?.result?.characterDetail?.id
                if (idCharacter == null) return
                if (idCharacter != event.characterId) {
                    viewModelScope.launch {
                        _effects.send(NavigateToCharacterDetail(event.characterId))
                    }
                }
            }
        }
    }

}