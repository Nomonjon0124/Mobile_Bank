package com.ummug.mobilebank.domain.entity.historycard

import java.math.BigInteger

data class Data(
    val amount: BigInteger,
    val id: Int,
    val is_output: Boolean
)