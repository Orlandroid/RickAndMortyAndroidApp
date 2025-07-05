package com.example.domain.usecases

import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.state.getData
import com.example.domain.state.isSuccess
import com.example.domain.utils.getListOfIdsOfCharacters
import com.example.domain.utils.isSingleCharacter
import javax.inject.Inject


class GetLocationDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository
) {
    suspend fun invoke(locationId: Int): LocationDetail {
        val locationResponse = locationRepository.getLocation(locationId)
        val idOfCharacters = getListOfIdsOfCharacters(locationResponse.residents)
        val characterResponse = if (idOfCharacters.isSingleCharacter()) {
            val mCharacter = characterRepository.getCharacter(idCharacter = idOfCharacters)
            if (mCharacter.isSuccess()) {
                listOf(mCharacter.getData())
            } else {
                emptyList()
            }
        } else {
            characterRepository.getManyCharacters(idsCharacters = idOfCharacters)
        }
        return LocationDetail(location = locationResponse, characters = characterResponse)
    }

    data class LocationDetail(
        val location: Location,
        val characters: List<Character>
    )
}