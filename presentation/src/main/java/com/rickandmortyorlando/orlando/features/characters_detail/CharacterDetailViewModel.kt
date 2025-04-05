package com.rickandmortyorlando.orlando.features.characters_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.usecases.GetCharacterDetailUseCase
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CharacterDetailUiState(
    val location: Location? = null,
    val characterDetail: Character,
    val characterOfThisLocation: List<Character>? = null
)

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val characterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<BaseViewState<CharacterDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()


    fun getCharacterDetailInfo(idCharacter: Int) = viewModelScope.launch(ioDispatcher) {
        runCatching {
            val characterDetail = characterDetailUseCase.invoke(idCharacter)
            _state.value = BaseViewState.Content(
                CharacterDetailUiState(
                    location = characterDetail.location,
                    characterDetail = characterDetail.characterDetail,
                    characterOfThisLocation = characterDetail.charactersOfThisLocation
                )
            )
        }.onFailure {
            _state.value = BaseViewState.Error(message = it.message.orEmpty())
        }
    }

}