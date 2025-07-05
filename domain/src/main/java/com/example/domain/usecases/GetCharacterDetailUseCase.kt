package com.example.domain.usecases

import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getData
import com.example.domain.state.isError
import com.example.domain.state.isSuccess
import com.example.domain.utils.getListOfEpisodes
import com.example.domain.utils.getListOfIdsOfCharacters
import com.example.domain.utils.getNumberFromUrWithPrefix
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val locationsRepository: LocationRepository
) {

    suspend fun invoke(idCharacter: Int): ApiResult<CharacterDetail> {
        val characterResponse = characterRepository.getCharacter(idCharacter.toString())
        val character = if (characterResponse.isSuccess()) {
            characterResponse.getData()
        } else {
            val errorMessage = (characterResponse as ApiResult.Error).msg
            return ApiResult.Error(msg = errorMessage)
        }
        val idsOfEpisodes = getListOfEpisodes(character.episode)
        if (character.hasNotLocation()) {
            return ApiResult.Success(
                data = CharacterDetail(
                    characterDetail = character,
                    idsOfEpisodes = idsOfEpisodes
                )
            )
        }
        val locationId = getNumberFromUrWithPrefix(
            urlWithNumberInTheFinalCharacter = character.originUrl.ifEmpty { character.urlLocation },
            prefix = "location"
        )
        val location = locationsRepository.getLocation(locationId)
        val listOfCharacters = getListOfIdsOfCharacters(location.residents)
        val charactersOfThisLocation = characterRepository.getManyCharacters(listOfCharacters)
        return ApiResult.Success(
            data = CharacterDetail(
                characterDetail = character,
                location = location,
                charactersOfThisLocation = charactersOfThisLocation,
                idsOfEpisodes = idsOfEpisodes
            )
        )
    }

    data class CharacterDetail(
        val characterDetail: Character? = null,
        val location: Location? = null,
        val charactersOfThisLocation: List<Character>? = null,
        val idsOfEpisodes: String
    )
}