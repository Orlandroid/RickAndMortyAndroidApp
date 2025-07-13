package com.example.domain.usecases

import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.FAIL_RESPONSE_FROM_SERVER
import com.example.domain.state.getData
import com.example.domain.state.getDataOrNull
import com.example.domain.state.isError
import com.example.domain.utils.getListOfIdsOfCharacters
import com.example.domain.utils.isSingleCharacter
import javax.inject.Inject


class GetLocationDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository
) {
    suspend fun invoke(locationId: Int): ApiResult<LocationDetail> {
        val locationResponse = locationRepository.getLocation(locationId)
        if (locationResponse.isError()) {
            return ApiResult.Error(msg = FAIL_RESPONSE_FROM_SERVER)
        }
        val location = locationResponse.getData()
        if (location.residents.isEmpty()) {
            return ApiResult.Error()
        }
        val idOfCharacters = getListOfIdsOfCharacters(location.residents)
        if (idOfCharacters.isSingleCharacter()) {
            val characterResponse = characterRepository.getCharacter(idCharacter = idOfCharacters)
            return ApiResult.Success(
                LocationDetail(
                    location = location,
                    characters = if (characterResponse.isError()) {
                        emptyList()
                    } else {
                        listOf(characterResponse.getData())
                    }
                )
            )
        }
        val characterResponse = characterRepository.getManyCharacters(idsCharacters = idOfCharacters)
        return ApiResult.Success(
            LocationDetail(
                location = location,
                characters = characterResponse.getDataOrNull()
            )
        )

    }

    data class LocationDetail(
        val location: Location,
        val characters: List<Character>? = null
    )
}