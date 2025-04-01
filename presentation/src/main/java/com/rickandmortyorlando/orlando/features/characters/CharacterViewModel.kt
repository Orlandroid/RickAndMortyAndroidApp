package com.rickandmortyorlando.orlando.features.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.di.CoroutineDispatchers
import com.example.domain.repository.CharacterRepository
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers,
    private val characterRepository: CharacterRepository,
) : BaseViewModel(coroutineDispatcher, networkHelper) {


    fun getCharacters() = characterRepository.getCharacters().cachedIn(viewModelScope)

}