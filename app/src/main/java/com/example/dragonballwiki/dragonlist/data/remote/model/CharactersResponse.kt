package com.example.dragonballwiki.dragonlist.data.remote.model


import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("items")
    val characters: List<CharacterResponse>
)