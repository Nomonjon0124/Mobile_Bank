package com.ummug.mobilebank.domain.entity.History

data class Data(
    val amount: Int,
    val card: Card,
    val id: Int,
    val is_output: Boolean
)