package com.example.dragonballwiki.dragonlist.data.remote.model


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("affiliation")
    val affiliation: String,
    @SerializedName("deletedAt")
    val deletedAt: Any,
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
    val race: String
)