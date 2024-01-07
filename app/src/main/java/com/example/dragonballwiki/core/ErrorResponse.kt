package com.example.dragonballwiki.core

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("cause")
    val cause: String?,
    @SerializedName("message")
    val message: String?
)