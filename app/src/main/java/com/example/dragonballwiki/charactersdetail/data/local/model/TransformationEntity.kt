package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class TransformationEntity (
    val id: Int,
    val image: String,
    val ki: String,
    val name: String
)