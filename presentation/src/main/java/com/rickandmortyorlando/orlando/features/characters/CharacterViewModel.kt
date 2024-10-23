package com.rickandmortyorlando.orlando.features.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.Repository
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.CharactersPagingSource
import com.example.data.pagination.CharactersSearchPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.local.SearchCharacter
import com.example.domain.models.remote.character.Character
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.di.CoroutineDispatchers
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: Repository,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
    private val rickAndMortyService: RickAndMortyService
) : BaseViewModel(coroutineDispatcher, networkHelper) {


    private val _manyCharactersResponse = MutableLiveData<ApiState<List<Character>>>()
    val manyCharactersResponse: LiveData<ApiState<List<Character>>>
        get() = _manyCharactersResponse

    var searchCharacter = SearchCharacter()

    fun getManyCharacters(ids: String) = viewModelScope.launch {
        safeApiCall(_manyCharactersResponse, coroutineDispatchers) {
            val response = repository.getManyCharacters(ids)
            withContext(Dispatchers.Main) {
                _manyCharactersResponse.value = ApiState.Success(response)
            }
        }
    }


    private lateinit var charactersPagingSource: CharactersPagingSource

    val getCharactersPagingSource: Flow<PagingData<Character>> =
        Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                charactersPagingSource = CharactersPagingSource(service = rickAndMortyService)
                charactersPagingSource
            }
        ).flow.cachedIn(viewModelScope)

    fun refreshCharactersPagingSource() = charactersPagingSource.invalidate()


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