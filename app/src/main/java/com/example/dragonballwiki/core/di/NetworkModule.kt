package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provider(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://dragonball-api.com/api/")
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