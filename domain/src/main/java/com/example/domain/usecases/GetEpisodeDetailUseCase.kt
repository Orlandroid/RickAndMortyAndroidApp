package com.example.domain.usecases

import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.example.domain.models.episodes.EpisodeImage
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.EpisodesRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.FAIL_RESPONSE_FROM_SERVER
import com.example.domain.state.getData
import com.example.domain.state.getDataOrNull
import com.example.domain.state.isError
import com.example.domain.utils.getListOfIdsOfCharacters
import javax.inject.Inject


class GetEpisodeDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val episodesRepository: EpisodesRepository
) {
    suspend fun invoke(episodeId: String): ApiResult<EpisodeDetail> {
        val episodeResponse = episodesRepository.getEpisode(episodeId)
        if (episodeResponse.isError()) {
            return ApiResult.Error(msg = FAIL_RESPONSE_FROM_SERVER)
        }
        val episode = episodeResponse.getData()
        val episodeImageResponse = episodesRepository.getImageOfEpisode(episode.name)
        val episodeImage = episodeImageResponse.getDataOrNull()
        val idOfCharactersOfThisEpisode = getListOfIdsOfCharacters(episode.characters)
        val charactersResponse = characterRepository.getManyCharacters(idOfCharactersOfThisEpisode)
        if (charactersResponse.isError()) {
            ApiResult.Success(EpisodeDetail(episode = episode, episodeImage = episodeImage))
        }
        return ApiResult.Success(
            EpisodeDetail(
                episode = episode,
                characters = charactersResponse.getData(),
                episodeImage = episodeImage
            )
        )
    }

    data class EpisodeDetail(
        val episode: Episode,
        val characters: List<Character> = emptyList(),
        val episodeImage: EpisodeImage? = null
    )
}