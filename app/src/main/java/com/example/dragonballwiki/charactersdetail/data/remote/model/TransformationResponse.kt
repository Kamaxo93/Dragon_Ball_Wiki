package com.example.dragonballwiki.charactersdetail.data.remote.model


import com.google.gson.annotations.SerializedName

data class TransformationResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("ki")
    val ki: String,
    @SerializedName("name")
    val name: String
)