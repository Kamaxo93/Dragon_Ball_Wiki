package com.example.dragonballwiki.core.di

import android.content.Context
import androidx.room.Room
import com.example.dragonballwiki.charactersdetail.data.local.dao.CharacterDao
import com.example.dragonballwiki.charactersdetail.data.local.dao.TransformationDao
import com.example.dragonballwiki.core.Constant.NAME_DATA_BASE
import com.example.dragonballwiki.core.database.DragonBallDataBase
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideCharacterListDao(dragonBallDataBase: DragonBallDataBase): CharacterListDao {
        return dragonBallDataBase.CharacterListDao()
    }

    @Provides
    fun provideCharacterDao(dragonBallDataBase: DragonBallDataBase): CharacterDao {
        return dragonBallDataBase.characterDao()
    }

    @Provides
    fun provideTransformationDao(dragonBallDataBase: DragonBallDataBase): TransformationDao {
        return dragonBallDataBase.transformationDao()
    }

    @Provides
    fun provideDragonBallDataBase(@ApplicationContext appContext: Context): DragonBallDataBase {
        return Room.databaseBuilder(appContext, DragonBallDataBase::class.java, NAME_DATA_BASE)
            .build()
    }

}