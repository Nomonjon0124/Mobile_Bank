package com.ummug.mobilebank.domain.entity

data class AddCardEntity(
    val expire_month: Int,
    val expire_year: Int,
    val name: String,
    val pan: String
)