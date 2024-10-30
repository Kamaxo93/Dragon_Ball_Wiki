package com.example.dragonballwiki.dragonlist.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val affiliation: String,
    val description: String,
    val gender: String,
    val image: String,
    val ki: String,
    val maxKi: String,
    val name: String,
    val race: String
)
