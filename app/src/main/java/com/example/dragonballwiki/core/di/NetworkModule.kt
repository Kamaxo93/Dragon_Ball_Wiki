package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.core.Constant.BASE_URL
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Módulo de red con Koin
 * Zantetsuken Arquitectónico: Timeouts optimizados para balance luz/oscuridad
 */
val networkModule = module {
    
    // OkHttpClient - Singleton
    single {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()
    }
    
    // Retrofit - Singleton
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    
    // DragonListService - Singleton
    single {
        get<Retrofit>().create(DragonListService::class.java)
    }
    
    // CharacterDetailService - Singleton
    single {
        get<Retrofit>().create(CharacterDetailService::class.java)
    }
}
