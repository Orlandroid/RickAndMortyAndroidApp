package com.rickandmortyorlando.orlando.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.CharactersSearchPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.mappers.toSearchCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class SearchCharacterUiState(
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val gender: String = "",
    val type: String = "",
    val isRefreshing: Boolean = false
)

sealed class SearchCharacterEvents {
    data class OnValueChange(val value: String) : SearchCharacterEvents()
    data object OnSendQuery : SearchCharacterEvents()
    data object OnClearQuery : SearchCharacterEvents()
    data class OnSwipeRefresh(val isRefreshing: Boolean) : SearchCharacterEvents()
}

@HiltViewModel
class SearchCharactersViewModel @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : ViewModel() {


    private val _uiState = MutableStateFlow(SearchCharacterUiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var charactersSearchPagingSource: CharactersSearchPagingSource

    val getCharactersSearchPagingSource: Flow<PagingData<Character>> =
        Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                charactersSearchPagingSource = CharactersSearchPagingSource(
                    service = rickAndMortyService,
                    search = uiState.value.toSearchCharacter()
                )
                charactersSearchPagingSource
            }
        ).flow.cachedIn(viewModelScope)


    fun handleEvents(event: SearchCharacterEvents) {
        when (event) {
            is SearchCharacterEvents.OnValueChange -> {
                _uiState.update {
                    it.copy(name = event.value)
                }
            }

            is SearchCharacterEvents.OnSendQuery -> {
                refreshCharactersSearchPagingSource()
            }

            is SearchCharacterEvents.OnClearQuery -> {
                _uiState.update {
                    it.copy(name = "")
                }
            }

            is SearchCharacterEvents.OnSwipeRefresh -> {
                _uiState.update {
                    it.copy(isRefreshing = event.isRefreshing)
                }
            }
        }
    }

    private fun refreshCharactersSearchPagingSource() = charactersSearchPagingSource.invalidate()


}