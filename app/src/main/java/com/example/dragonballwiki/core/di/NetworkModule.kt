package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.core.Constant.BASE_URL
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provider(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            // Zantetsuken Arquitectónico: Timeouts optimizados para balance luz/oscuridad
            .connectTimeout(5, TimeUnit.SECONDS)  // Tiempo para establecer conexión TCP
            .readTimeout(5, TimeUnit.SECONDS)     // Tiempo para leer respuesta del servidor
            .writeTimeout(5, TimeUnit.SECONDS)    // Tiempo para enviar request al servidor
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideDragonListService(retrofit: Retrofit): DragonListService {
        return retrofit.create(DragonListService::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterDetailService(retrofit: Retrofit): CharacterDetailService {
        return retrofit.create(CharacterDetailService::class.java)
    }

}