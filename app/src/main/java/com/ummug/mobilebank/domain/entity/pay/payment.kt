package com.ummug.mobilebank.domain.entity.pay

data class payment(
    val amount: Int,
    val card_id: Int,
    val payment_type_id: Int,
    val phone_number: String
)