package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.data.api.Api
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.CardNameUpdate
import com.ummug.mobilebank.domain.entity.History.HistoryRsponse
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import com.ummug.mobilebank.domain.entity.pay.payment
import com.ummug.mobilebank.domain.entity.profile.My_about
import com.ummug.mobilebank.domain.entity.profile.UpdatePaswordRequest
import com.ummug.mobilebank.domain.entity.profile.UpdatePhone
import com.ummug.mobilebank.domain.entity.profile.UpdatePhoneRespons
import com.ummug.mobilebank.domain.entity.profile.UpdateRequest
import com.ummug.mobilebank.domain.entity.transfer.TransferEntity
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import retrofit2.Response
import javax.inject.Inject

class CardsDataSourceImpl @Inject constructor(
    private val authApi: Api,
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

    override suspend fun listHistory(bearerToken: String): Response<HistoryRsponse> {
        return authApi.listhistory(bearerToken)
    }

    override suspend fun My_about(bearerToken: String): Response<My_about> {
        return authApi.Account_about(bearerToken)
    }

    override suspend fun Update_Fuul(
        updateRequest: UpdateRequest,
        bearerToken: String
    ): Response<String> {
        return authApi.UpdateFullName(updateRequest, bearerToken)
    }


    override suspend fun Update_parol(
        updatePaswordRequest: UpdatePaswordRequest,
        bearerToken: String
    ): Response<String> {
       return authApi.UpdatePasword(updatePaswordRequest,bearerToken)
    }

    override suspend fun Update_phone(
        updatePhone: UpdatePhone,
        bearerToken: String
    ): Response<UpdatePhoneRespons> {
        return authApi.UpdatePhone(updatePhone, bearerToken)
    }

    override suspend fun Update_phone_very(
        updatePhoneRespons: UpdatePhoneRespons,
        bearerToken: String
    ): Response<String> {
        return authApi.UpdatePhoneVerify(updatePhoneRespons, bearerToken)
    }

    override suspend fun PaymentAmount(
        payment: payment,
        bearerToken: String
    ): Response<UpdatePhoneRespons> {
        return authApi.PaymentAmount(payment,bearerToken)
    }

    override suspend fun PaymentVerify(
        updatePhoneRespons: UpdatePhoneRespons,
        bearerToken: String
    ): Response<String> {
        return authApi.PaymentVerify(updatePhoneRespons, bearerToken)
    }


}