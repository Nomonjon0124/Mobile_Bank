package com.ummug.mobilebank.domain.entity

data class SignUpResponse(
    val token: String,
    val code: String
)