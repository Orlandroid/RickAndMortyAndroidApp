package com.example.domain.usecases

import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.example.domain.models.episodes.EpisodeImage
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.EpisodesRepository
import com.example.domain.utils.getListOfIdsOfCharacters
import javax.inject.Inject


class GetEpisodeDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val episodesRepository: EpisodesRepository
) {
    suspend fun invoke(episodeId: String): EpisodeDetail {
        val episodeResponse = episodesRepository.getEpisode(episodeId)
        val episodeImageResponse = episodesRepository.getImageOfEpisode(episodeResponse.name)
        val idOfCharactersOfThisEpisode = getListOfIdsOfCharacters(episodeResponse.characters)
        val characters = characterRepository.getManyCharacters(idOfCharactersOfThisEpisode)
        return EpisodeDetail(
            episode = episodeResponse,
            characters = characters,
            episodeImage = episodeImageResponse
        )
    }

    data class EpisodeDetail(
        val episode: Episode,
        val characters: List<Character>,
        val episodeImage: EpisodeImage
    )
}