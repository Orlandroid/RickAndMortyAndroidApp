package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.data.api.RickAndMortyEpisodesImages
import com.example.data.api.RickAndMortyService
import com.example.data.model.character.toEpisodeImage
import com.example.data.model.episode.toEpisode
import com.example.data.pagination.EpisodesPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.episodes.Episode
import com.example.domain.models.episodes.EpisodeImage
import com.example.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val api: RickAndMortyService,
    private val episodeImageService: RickAndMortyEpisodesImages
) : EpisodesRepository {

    private lateinit var episodesPagingSource: EpisodesPagingSource


    override fun getEpisodes(): Flow<PagingData<Episode>> {
        return Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                episodesPagingSource = EpisodesPagingSource(service = api)
                episodesPagingSource
            }
        ).flow
    }

    override suspend fun getEpisode(ids: String): Episode {
        val baseUrl = "https://rickandmortyapi.com/api/episode/$ids"
        return api.getSingleEpisode(baseUrl).toEpisode()
    }

    override suspend fun getManyEpisodes(ids: String): List<Episode> {
        val baseUrl = "https://rickandmortyapi.com/api/episode/$ids"
        return if (ids.contains(",")) {
            api.getManyEpisodes(baseUrl).map { it.toEpisode() }
        } else {
            listOf(api.getSingleEpisode(baseUrl).toEpisode())
        }
    }

    override suspend fun getImageOfEpisode(episodeName: String): EpisodeImage {
        return episodeImageService.getImagesEpisodes().first { it.name.equals(episodeName, true) }
            .toEpisodeImage()
    }

}