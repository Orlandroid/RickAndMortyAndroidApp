package com.example.paggingexample.di

import com.example.paggingexample.data.Repository
import com.example.paggingexample.data.api.RickAndMortyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = " https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideRickAndMortyService(retrofit: Retrofit): RickAndMortyService =
        retrofit.create(RickAndMortyService::class.java)


    @Singleton
    @Provides
    fun provideRepository(rickAndMortyService: RickAndMortyService): Repository =
        Repository(rickAndMortyService)

}