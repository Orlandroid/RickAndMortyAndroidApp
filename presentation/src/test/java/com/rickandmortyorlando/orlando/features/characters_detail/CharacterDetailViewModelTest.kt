package com.rickandmortyorlando.orlando.features.characters_detail

import com.example.domain.utils.getListOfEpisodes
import org.junit.Test

class CharacterDetailViewModelTest {


    @Test
    fun `get list of episodes`() {
        //Given
        val episodesList = arrayListOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2",
            "https://rickandmortyapi.com/api/episode/3",
            "https://rickandmortyapi.com/api/episode/4",
            "https://rickandmortyapi.com/api/episode/10",
            "https://rickandmortyapi.com/api/episode/11",
            "https://rickandmortyapi.com/api/episode/12",
        )

        val expectResult = "1,2,3,4,10,11,12"
        val idsOfEpisodesOfTheCharacter = getListOfEpisodes(episodesList)
        //When


        //Then
        assert(expectResult == idsOfEpisodesOfTheCharacter)
    }

    @Test
    fun getSingleLocation() {
        
    }

}