package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dragonballwiki.charactersdetail.data.local.model.converte.OriginPlanetConverter


data class OriginPlanetEntity(
    val id: Int,
    val description: String,
    val image: String,
    val isDestroyed: Boolean,
    val name: String
)