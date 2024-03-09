package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<HttpLoggingInterceptor>())
        }.build()
    }
    single {
        Retrofit.Builder().baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }
    single {
        get<Retrofit>().create<DragonListService>()
    }
    single {
        get<Retrofit>().create<CharacterDetailService>()
    }
}