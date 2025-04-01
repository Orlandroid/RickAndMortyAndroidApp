package com.rickandmortyorlando.orlando.di


import com.apollographql.apollo3.ApolloClient
import com.example.data.Repository
import com.example.data.api.RickAndMortyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRickAndMortyService(retrofit: Retrofit): RickAndMortyService =
        retrofit.create(RickAndMortyService::class.java)


    @Singleton
    @Provides
    fun provideRepository(rickAndMortyService: RickAndMortyService): Repository =
        Repository(rickAndMortyService)

    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient = ApolloClient.builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()

}