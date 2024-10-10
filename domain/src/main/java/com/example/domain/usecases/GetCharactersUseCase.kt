package com.example.domain.usecases

class GetCountriesUseCase(private val repository: String) {

    suspend operator fun invoke() = repository

}