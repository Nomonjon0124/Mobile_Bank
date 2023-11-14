package com.ummug.mobilebank.domain.entity.profile

data class UpdatePaswordRequest(
    val current_password: String,
    val password: String,
    val password_confirmation: String
)