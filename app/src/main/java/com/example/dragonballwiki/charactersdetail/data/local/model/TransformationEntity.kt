package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TransformationEntity(
    @PrimaryKey
    val id: Int,
    val image: String,
    val ki: String,
    val name: String
)