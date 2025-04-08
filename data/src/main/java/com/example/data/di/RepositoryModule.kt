package com.example.data.di


import com.example.data.api.RickAndMortyEpisodesImages
import com.example.data.api.RickAndMortyService
import com.example.data.repository.CharacterRepositoryImpl
import com.example.data.repository.EpisodesRepositoryImpl
import com.example.data.repository.LocationsRepositoryImpl
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
    ): CharacterRepository = CharacterRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideEpisodesRepository(
        api: RickAndMortyService,
        episodeImageService: RickAndMortyEpisodesImages
    ): EpisodesRepository =
        EpisodesRepositoryImpl(api = api, episodeImageService = episodeImageService)

    @Provides
    @Singleton
    fun provideLocationsRepository(
        api: RickAndMortyService,
    ): LocationRepository = LocationsRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideRickAndMortyService(retrofit: Retrofit): RickAndMortyService =
        retrofit.create(RickAndMortyService::class.java)

    @Singleton
    @Provides
    fun provideRickAndMortyEpisodesImages(@Named("episodes") retrofit: Retrofit): RickAndMortyEpisodesImages =
        retrofit.create(RickAndMortyEpisodesImages::class.java)

}