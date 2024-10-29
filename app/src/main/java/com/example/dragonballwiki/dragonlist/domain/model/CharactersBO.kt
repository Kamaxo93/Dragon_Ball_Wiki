package com.example.dragonballwiki.dragonlist.domain.model

data class CharacterBO(
    val affiliation: String,
    val description: String,
    val gender: String,
    val id: Int,
    val image: String,
    val ki: String,
    val maxKi: String,
    val name: String,
    val race: String
)