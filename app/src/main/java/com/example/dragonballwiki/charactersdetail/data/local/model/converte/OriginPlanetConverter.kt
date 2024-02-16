package com.example.dragonballwiki.charactersdetail.data.local.model.converte

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dragonballwiki.charactersdetail.ui.model.OriginPlanet
import com.google.gson.Gson

@ProvidedTypeConverter
class OriginPlanetConverter {
    @TypeConverter
    fun StringToOriginPlanet(originPlanetString: String?): OriginPlanet = Gson().fromJson(originPlanetString, OriginPlanet::class.java)


    @TypeConverter
    fun OriginPlanetToString(originPlanet: OriginPlanet?) = Gson().toJson(originPlanet)
}