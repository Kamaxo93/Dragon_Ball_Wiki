package com.example.dragonballwiki.charactersdetail.data.remote.model


import com.google.gson.annotations.SerializedName

data class OriginPlanetResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?
)