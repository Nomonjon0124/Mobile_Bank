package com.ummug.mobilebank.domain.entity.transfer

data class TransferRequest(
    val amount: Int,
    val from_card_id: Int,
    val pan: String
)