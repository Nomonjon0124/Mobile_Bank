package com.ummug.mobilebank.data.repository.Transfer

import com.ummug.mobilebank.datasource.CardsDataSource
import com.ummug.mobilebank.domain.entity.transfer.TransferEntity
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import retrofit2.Response
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val cardsDataSource: CardsDataSource
):TransferRepository {
    override var transferToken: String?
        get() = cardsDataSource.transferToken
        set(value) {
            cardsDataSource.transferToken=value
        }
    override var transfercode: String?
        get() = cardsDataSource.transfercode
        set(value) {
            cardsDataSource.transfercode=value
        }

    override suspend fun transferMoney(
        transferEntity: TransferEntity,
        bearerToken: String
    ): Response<TransferRespons> {
        return cardsDataSource.transferMoney(transferEntity, bearerToken)
    }

    override suspend fun verifyTransfer(
        transferRespons: TransferRespons,
        bearerToken: String
    ): Response<String> {
       return cardsDataSource.transferVerify(transferRespons, bearerToken)
    }


}