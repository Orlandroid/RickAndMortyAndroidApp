package com.example.data.di


import com.example.data.api.RickAndMortyService
import com.example.data.repository.CharacterRepositoryImpl
import com.example.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharactersRepository(
        api: RickAndMortyService,
    ): CharacterRepository = CharacterRepositoryImpl(api)

}