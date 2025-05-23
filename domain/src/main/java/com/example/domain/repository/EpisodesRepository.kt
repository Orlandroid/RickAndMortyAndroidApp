package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.episodes.Episode
import com.example.domain.models.episodes.EpisodeImage
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    fun getEpisodes(): Flow<PagingData<Episode>>

    suspend fun getEpisode(ids: String): Episode

    suspend fun getManyEpisodes(ids: String): List<Episode>

    suspend fun getImageOfEpisode(episodeName: String): EpisodeImage
}