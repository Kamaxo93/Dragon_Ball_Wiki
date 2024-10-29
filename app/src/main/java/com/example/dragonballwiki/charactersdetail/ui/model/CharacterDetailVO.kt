package com.example.dragonballwiki.charactersdetail.ui.model

data class CharacterDetailVO(
    val affiliation: String,
    val description: String,
    val gender: String,
    val id: Int,
    val image: String,
    val ki: String,
    val maxKi: String,
    val name: String,
    val race: String,
    val transformations: List<Transformation>
)