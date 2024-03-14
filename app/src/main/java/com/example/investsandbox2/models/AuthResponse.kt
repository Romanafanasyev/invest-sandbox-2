package com.example.investsandbox2.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("error")
    val error: String?
)
