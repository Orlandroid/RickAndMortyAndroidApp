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
import com.example.domain.models.characters.SearchCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


sealed class SearchCharacterEvents {
    data class OnValueChange(val value: String) : SearchCharacterEvents()
    data object OnSendQuery : SearchCharacterEvents()
    data object OnClearQuery : SearchCharacterEvents()
}

@HiltViewModel
class SearchCharactersViewModel @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : ViewModel() {


    private val _uiState = MutableStateFlow(SearchCharacter())
    val uiState = _uiState.asStateFlow()

    private lateinit var charactersSearchPagingSource: CharactersSearchPagingSource

    val getCharactersSearchPagingSource: Flow<PagingData<Character>> =
        Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                charactersSearchPagingSource = CharactersSearchPagingSource(
                    service = rickAndMortyService,
                    search = uiState.value
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
        }
    }

    private fun refreshCharactersSearchPagingSource() = charactersSearchPagingSource.invalidate()


}