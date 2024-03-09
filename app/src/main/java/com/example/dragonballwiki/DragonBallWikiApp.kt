package com.example.dragonballwiki

import android.app.Application
import com.example.dragonballwiki.core.di.dataBaseModule
import com.example.dragonballwiki.core.di.dataSourceModule
import com.example.dragonballwiki.core.di.networkModule
import com.example.dragonballwiki.core.di.repositoryModule
import com.example.dragonballwiki.core.di.useCaseModule
import com.example.dragonballwiki.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DragonBallWikiApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DragonBallWikiApp)
            modules(dataBaseModule, dataSourceModule, networkModule, repositoryModule, useCaseModule, viewModelModule)
        }
    }
}