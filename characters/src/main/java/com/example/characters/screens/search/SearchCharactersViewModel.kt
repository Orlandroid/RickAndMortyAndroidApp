package com.example.characters.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.example.domain.models.characters.Character
import com.example.domain.models.characters.SearchCharacter
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest


data class SearchCharacterUiState(
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val gender: String = "",
    val type: String = "",
    val isRefreshing: Boolean = false,
    val totalOfItemForSearch: Int? = null,
    val hasPerformedSearch: Boolean = false
)

sealed class SearchCharacterEvents {
    data class OnValueChange(val value: String) : SearchCharacterEvents()
    data object OnSendQuery : SearchCharacterEvents()
    data object OnClearQuery : SearchCharacterEvents()
    data class OnSwipeRefresh(val isRefreshing: Boolean) : SearchCharacterEvents()
}


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchCharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(SearchCharacterUiState())
    val uiState = _uiState.asStateFlow()


    private val searchQuery = MutableStateFlow(SearchCharacter())

    val charactersFlow: Flow<PagingData<Character>> =
        searchQuery
            .flatMapLatest { search -> repository.searchCharacter(search) }
            .cachedIn(viewModelScope)


    fun handleEvents(event: SearchCharacterEvents) {
        when (event) {
            is SearchCharacterEvents.OnValueChange -> {
                _uiState.update {
                    it.copy(name = event.value)
                }
            }

            is SearchCharacterEvents.OnSendQuery -> {
                _uiState.update {
                    it.copy(hasPerformedSearch = true)
                }
                searchQuery.value = SearchCharacter(
                    name = uiState.value.name,
                    status = uiState.value.status,
                    species = uiState.value.species,
                    gender = uiState.value.gender,
                    type = uiState.value.type
                )
            }

            is SearchCharacterEvents.OnClearQuery -> {
                _uiState.update {
                    SearchCharacterUiState()
                }

                searchQuery.value = SearchCharacter()
            }

            is SearchCharacterEvents.OnSwipeRefresh -> {
                _uiState.update {
                    it.copy(isRefreshing = event.isRefreshing)
                }
                searchQuery.value = searchQuery.value
            }
        }
    }


}