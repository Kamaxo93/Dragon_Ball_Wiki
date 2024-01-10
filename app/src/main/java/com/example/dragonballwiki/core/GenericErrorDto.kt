package com.example.dragonballwiki.core

import com.google.gson.annotations.SerializedName

data class GenericErrorDto(
    @SerializedName("estado") val status: String,
    @SerializedName("mensaje") val message: String,
    @SerializedName("detalles") val details: List<String>?,
)
