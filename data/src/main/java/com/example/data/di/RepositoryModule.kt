package com.example.data.di


import com.example.data.api.RickAndMortyEpisodesImages
import com.example.data.api.RickAndMortyService
import com.example.data.repository.RemoteCharacterRepository
import com.example.data.repository.RemoteEpisodesRepository
import com.example.data.repository.RemoteLocationsRepository
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.EpisodesRepository
import com.example.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharactersRepository(
        api: RickAndMortyService,
    ): CharacterRepository = RemoteCharacterRepository(api)

    @Provides
    @Singleton
    fun provideEpisodesRepository(
        api: RickAndMortyService,
        episodeImageService: RickAndMortyEpisodesImages
    ): EpisodesRepository =
        RemoteEpisodesRepository(api = api, episodeImageService = episodeImageService)

    @Provides
    @Singleton
    fun provideLocationsRepository(
        api: RickAndMortyService,
    ): LocationRepository = RemoteLocationsRepository(api)

    @Singleton
    @Provides
    fun provideRickAndMortyService(retrofit: Retrofit): RickAndMortyService =
        retrofit.create(RickAndMortyService::class.java)

    @Singleton
    @Provides
    fun provideRickAndMortyEpisodesImages(@Named("episodes") retrofit: Retrofit): RickAndMortyEpisodesImages =
        retrofit.create(RickAndMortyEpisodesImages::class.java)

}