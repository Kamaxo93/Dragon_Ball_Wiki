package com.example.dragonballwiki.core.di

/**
 * Módulo central que agrupa todos los módulos de la aplicación
 * Facilita la inicialización en DragonBallWikiApp
 * 
 * Migrado de Hilt a Koin - Zantetsuken Arquitectónico
 */
val appModules = listOf(
    dispatcherModule,
    networkModule,
    dataBaseModule,
    dataSourceModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
