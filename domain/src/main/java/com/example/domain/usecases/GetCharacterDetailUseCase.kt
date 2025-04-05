package com.example.domain.usecases

import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.utils.getListOfIdsOfCharacters
import com.example.domain.utils.getNumberFromUrWithPrefix
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val locationsRepository: LocationRepository
) {

    private fun characterHasLocation(urlLocation: String): Boolean {
        return urlLocation.isNotEmpty()
    }

    suspend fun invoke(idCharacter: Int): CharacterDetail {
        val character = characterRepository.getCharacter(idCharacter.toString())
        var location: Location? = null
        var charactersOfThisLocation:List<Character>? = null
        if (characterHasLocation(character.urlLocation)) {
            val locationId = getNumberFromUrWithPrefix(
                urlWithNumberInTheFinalCharacter = character.originUrl.ifEmpty { character.urlLocation },
                prefix = "location"
            )
            location = locationsRepository.getLocation(locationId)
            val listOfCharacters = getListOfIdsOfCharacters(location.residents)
             charactersOfThisLocation = characterRepository.getManyCharacters(listOfCharacters)
        }
        return CharacterDetail(characterDetail = character, location = location, charactersOfThisLocation = charactersOfThisLocation)
    }

    data class CharacterDetail(
        val characterDetail: Character,
        val location: Location? = null,
        val charactersOfThisLocation: List<Character>? = null
    )
}