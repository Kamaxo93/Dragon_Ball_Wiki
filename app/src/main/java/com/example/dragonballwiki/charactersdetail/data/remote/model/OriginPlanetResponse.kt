package com.example.dragonballwiki.charactersdetail.data.remote.model


import com.google.gson.annotations.SerializedName

data class OriginPlanetResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("isDestroyed")
    val isDestroyed: Boolean,
    @SerializedName("name")
    val name: String
)