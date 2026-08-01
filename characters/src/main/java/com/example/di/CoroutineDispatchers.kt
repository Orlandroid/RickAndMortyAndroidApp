package com.example.di

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Singleton
class CoroutineDispatchers @Inject constructor(
    @param:DefaultDispatcher
    val default: CoroutineDispatcher,

    @param:MainDispatcher
    val main: CoroutineDispatcher,

    @param:IoDispatcher
    val io: CoroutineDispatcher,

    @param:MainImmediateDispatcher
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
