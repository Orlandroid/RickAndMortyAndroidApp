package com.rickandmortyorlando.orlando.ui.characters

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.Repository
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.CharactersPagingSource
import com.example.domain.models.local.SearchCharacter
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.character.CharacterResponse
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.ui.main.NetworkHelper
import com.rickandmortyorlando.orlando.utils.ErrorType
import com.rickandmortyorlando.orlando.utils.getTypeOfError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper,
    private val rickAndMortyService: RickAndMortyService
) : ViewModel() {

    private val _myCharacterResponse = MutableLiveData<ApiState<CharacterResponse>>()
    val myCharacterResponse: LiveData<ApiState<CharacterResponse>>
        get() = _myCharacterResponse

    private val _searchCharacterResponse = MutableLiveData<ApiState<CharacterResponse>>()
    val searchCharacterResponse: LiveData<ApiState<CharacterResponse>>
        get() = _searchCharacterResponse

    private val _manyCharactersResponse = MutableLiveData<ApiState<List<Character>>>()
    val manyCharactersResponse: LiveData<ApiState<List<Character>>>
        get() = _manyCharactersResponse

    @SuppressLint("NullSafeMutableLiveData")
    fun getCharacters(page: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _myCharacterResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _myCharacterResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getCharacters(page.toInt())
                withContext(Dispatchers.Main) {
                    _myCharacterResponse.value = ApiState.Success(response)
                    _myCharacterResponse.value = null
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _myCharacterResponse.value = ApiState.Error(e)
                }
            }
        }
    }


    @SuppressLint("NullSafeMutableLiveData")
    fun getManyCharacters(ids: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _manyCharactersResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _manyCharactersResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getManyCharacters(ids)
                withContext(Dispatchers.Main) {
                    _manyCharactersResponse.value = ApiState.Success(response)
                    _manyCharactersResponse.value = null
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _manyCharactersResponse.value = ApiState.Error(e)
                }
            }
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun searchCharacters(
        searchCharacter: SearchCharacter,
        page: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _searchCharacterResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _searchCharacterResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.searchCharacter(searchCharacter, page)
                withContext(Dispatchers.Main) {
                    _searchCharacterResponse.value = ApiState.Success(response)
                    _searchCharacterResponse.value = null
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    val errorType = getTypeOfError(e)
                    if (errorType is ErrorType.HttpException) {
                        _searchCharacterResponse.value =
                            ApiState.Error(msg = e, codeError = errorType.responseCode)
                    } else {
                        _searchCharacterResponse.value = ApiState.Error(e)
                    }
                }
            }
        }
    }

    private lateinit var charactersPagingSource: CharactersPagingSource

    fun getCharactersPagingSource(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = Repository.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = Repository.PRE_FETCH_DISTANCE
            ),
            pagingSourceFactory = {
                charactersPagingSource = CharactersPagingSource(service = rickAndMortyService)
                charactersPagingSource
            }
        ).flow
    }

    fun getCharactersPaging(): Flow<PagingData<Character>> {
        return repository.getCharactersPagingSource()
    }

    fun refreshCharactersPagingSource() = charactersPagingSource.invalidate()


}