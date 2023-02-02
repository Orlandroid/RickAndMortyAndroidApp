package com.example.paggingexample.di

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Singleton
class CoroutineDispatchers @Inject constructor(
    @DefaultDispatcher
    val default: CoroutineDispatcher,

    @MainDispatcher
    val main: CoroutineDispatcher,

    @IoDispatcher
    val io: CoroutineDispatcher,

    @MainImmediateDispatcher
    val immediate: CoroutineDispatcher,
)

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainImmediateDispatcher
