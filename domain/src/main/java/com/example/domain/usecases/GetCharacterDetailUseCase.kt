package com.example.domain.usecases

import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.FAIL_RESPONSE_FROM_SERVER
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
        if (characterResponse.isError()) {
            return ApiResult.Error(msg = FAIL_RESPONSE_FROM_SERVER)
        }
        val character = characterResponse.getData()
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
        val locationResponse = locationsRepository.getLocation(locationId)
        if (locationResponse.isError()) {
            return ApiResult.Success(
                data = CharacterDetail(
                    characterDetail = character,
                    idsOfEpisodes = idsOfEpisodes
                )
            )
        }
        val location = locationResponse.getData()
        val listOfCharacters = getListOfIdsOfCharacters(locationResponse.getData().residents)
        val charactersOfThisLocationResponse =
            characterRepository.getManyCharacters(listOfCharacters)
        val charactersOfThisLocation = if (charactersOfThisLocationResponse.isSuccess()) {
            charactersOfThisLocationResponse.getData()
        } else {
            emptyList()
        }
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