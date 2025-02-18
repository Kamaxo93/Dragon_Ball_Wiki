package com.example.dragonballwiki.charactersdetail.data.remote.model


import com.google.gson.annotations.SerializedName

data class CharacterDetailResponse(
    @SerializedName("affiliation")
    val affiliation: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("ki")
    val ki: String,
    @SerializedName("maxKi")
    val maxKi: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("race")
    val race: String,
    @SerializedName("transformations")
    val transformations: List<TransformationResponse>,
    @SerializedName("originPlanet")
    val originPlanet: OriginPlanetResponse
)