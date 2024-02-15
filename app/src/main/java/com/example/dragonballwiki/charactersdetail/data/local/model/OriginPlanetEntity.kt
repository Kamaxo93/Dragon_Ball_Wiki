package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class OriginPlanetEntity(
    @PrimaryKey
    val id: Int,
    val description: String,
    val image: String,
    val isDestroyed: Boolean,
    val name: String
)