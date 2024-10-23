package com.rickandmortyorlando.orlando.features.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.CharactersSearchPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.local.SearchCharacter
import com.example.domain.models.remote.character.Character
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchCharactersViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
    private val rickAndMortyService: RickAndMortyService
) : BaseViewModel(coroutineDispatcher, networkHelper) {


    var searchCharacter = SearchCharacter()

    private lateinit var charactersSearchPagingSource: CharactersSearchPagingSource

    val getCharactersSearchPagingSource: Flow<PagingData<Character>> =
        Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                charactersSearchPagingSource = CharactersSearchPagingSource(
                    service = rickAndMortyService,
                    search = searchCharacter
                )
                charactersSearchPagingSource
            }
        ).flow.cachedIn(viewModelScope)

    fun refreshCharactersSearchPagingSource() = charactersSearchPagingSource.invalidate()


}