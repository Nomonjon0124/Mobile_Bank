package com.ummug.mobilebank.domain.entity

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("code")
    val code: String
)