package com.ummug.mobilebank.domain.entity.transfer

data class Errors(
    val code: List<String>,
    val token: List<String>
)