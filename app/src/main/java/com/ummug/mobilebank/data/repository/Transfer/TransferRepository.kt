package com.ummug.mobilebank.data.repository.Transfer

import com.ummug.mobilebank.domain.entity.transfer.TransferEntity
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import retrofit2.Response

interface TransferRepository {

    var transferToken: String?
    var transfercode: String?

    suspend fun transferMoney(transferEntity: TransferEntity,bearerToken: String):Response<TransferRespons>

    suspend fun verifyTransfer(transferRespons: TransferRespons,bearerToken: String):Response<String>
}