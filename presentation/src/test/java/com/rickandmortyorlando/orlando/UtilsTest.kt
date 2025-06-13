package com.rickandmortyorlando.orlando

import com.example.domain.utils.getListOfEpisodes
import com.rickandmortyorlando.orlando.utils.getColorStatusResource
import org.junit.Test

class UtilsTest {

    @Test
    fun `get color status Resource`() {
        val aliveColor = R.color.alive
        val deadColor = R.color.dead
        val unknownColor = R.color.unknown
        val defaultColor = R.color.unknown

        val defaultColorStatus = getColorStatusResource()
        val aliveColorStatus = getColorStatusResource("Alive")
        val deadColorStatus = getColorStatusResource("Dead")
        val unknownColorStatus = getColorStatusResource("unknown")


        assert(defaultColorStatus == defaultColor)
        assert(aliveColorStatus == aliveColor)
        assert(deadColorStatus == deadColor)
        assert(unknownColorStatus == unknownColor)
    }

    @Test
    fun `given one list of episodes get al of ids of those episodes`() {
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
    fun ` given one url of episode get the number of the episode`() {
        //Given
        val episodesList = arrayListOf(
            "https://rickandmortyapi.com/api/episode/27",
        )

        val expectResult = "27"
        val idsOfEpisodesOfTheCharacter = getListOfEpisodes(episodesList)
        //When


        //Then
        assert(expectResult == idsOfEpisodesOfTheCharacter)
    }


}