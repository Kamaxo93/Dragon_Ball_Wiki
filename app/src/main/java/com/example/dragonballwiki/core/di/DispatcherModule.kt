package com.example.dragonballwiki.core.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Módulo DI para proveer CoroutineDispatchers con Koin
 * 
 * Migrado de Hilt a Koin usando named qualifiers
 * - @IoDispatcher → named("IoDispatcher")
 * - @MainDispatcher → named("MainDispatcher")
 * - @DefaultDispatcher → named("DefaultDispatcher")
 * 
 * Esto permite:
 * - Testear ViewModels inyectando TestDispatchers
 * - Cambiar estrategias de threading sin modificar código
 * - Seguir mejores prácticas de Clean Architecture
 */
val dispatcherModule = module {
    single(named(DispatcherQualifiers.IO)) { Dispatchers.IO }
    single(named(DispatcherQualifiers.MAIN)) { Dispatchers.Main }
    single(named(DispatcherQualifiers.DEFAULT)) { Dispatchers.Default }
}

/**
 * Constantes para evitar magic strings en named qualifiers
 */
object DispatcherQualifiers {
    const val IO = "IoDispatcher"
    const val MAIN = "MainDispatcher"
    const val DEFAULT = "DefaultDispatcher"
}
