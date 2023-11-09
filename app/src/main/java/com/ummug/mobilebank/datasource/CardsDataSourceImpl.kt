package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.data.api.AuthApi
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.CardNameUpdate
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import com.ummug.mobilebank.domain.entity.transfer.TransferEntity
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import retrofit2.Response
import javax.inject.Inject

class CardsDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
    private val settings: Settings
):CardsDataSource {
    override var transferToken: String?
        get() = settings.transfertoken
        set(value) {
            settings.transfertoken=value
        }
    override var transfercode: String?
        get() = settings.transfercode
        set(value) {
            settings.transfercode=value
        }

    override suspend fun getCards(bearerToken: String): GetCardsesponse {
        return authApi.getCards(bearerToken)
    }

    override suspend fun addCard(addCardEntity: AddCardEntity, bearerToken: String): Response<CardResponse> {
        return authApi.addCard(addCardEntity,bearerToken)
    }

    override suspend fun delete(id: String, bearerToken: String): Response<String> {
        return authApi.deleteCard(id,bearerToken)
    }

    override suspend fun update(
        cardNameUpdate: CardNameUpdate,
        id: String,
        bearerToken: String
    ): Response<CardResponse> {
        return authApi.updateCard(id,cardNameUpdate,bearerToken)
    }

    override suspend fun transferMoney(
        transferEntity: TransferEntity,
        bearerToken: String
    ): Response<TransferRespons> {
        return authApi.transferMoney(transferEntity,bearerToken)
    }

    override suspend fun transferVerify(
        transferRespons: TransferRespons,
        bearerToken: String
    ): Response<String> {
        return authApi.verifyTransfer(transferRespons,bearerToken)
    }


}