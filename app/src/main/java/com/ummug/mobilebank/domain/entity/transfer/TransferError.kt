package com.ummug.mobilebank.domain.entity.transfer

data class TransferError(
    val errors: Errors,
    val message: String
)