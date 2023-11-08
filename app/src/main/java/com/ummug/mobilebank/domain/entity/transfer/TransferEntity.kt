package com.ummug.mobilebank.domain.entity.transfer

data class TransferEntity(
    val amount: Int,
    val from_card_id: Int,
    val pan: String
)