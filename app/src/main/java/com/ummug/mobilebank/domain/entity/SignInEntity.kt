package com.ummug.mobilebank.domain.entity

import com.google.gson.annotations.SerializedName

data class SignInEntity (
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String
)
