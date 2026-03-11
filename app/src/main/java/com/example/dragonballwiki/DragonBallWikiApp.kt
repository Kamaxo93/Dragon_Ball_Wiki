package com.example.dragonballwiki

import android.app.Application
import com.example.dragonballwiki.core.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Application class con inicialización de Koin
 * Migrado de Hilt a Koin - Zantetsuken Arquitectónico
 */
class DragonBallWikiApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Inicializar Koin
        startKoin {
            // Logger de Koin (solo en debug)
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            
            // Proveer contexto de Android
            androidContext(this@DragonBallWikiApp)
            
            // Cargar todos los módulos
            modules(appModules)
        }
    }
}
