package com.ummug.mobilebank.data.repository

import com.ummug.mobilebank.datasource.CardsDataSource
import com.ummug.mobilebank.domain.CardNameUpdate
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.History.HistoryRsponse
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import com.ummug.mobilebank.domain.entity.profile.My_about
import com.ummug.mobilebank.domain.entity.profile.UpdatePaswordRequest
import com.ummug.mobilebank.domain.entity.profile.UpdatePhone
import com.ummug.mobilebank.domain.entity.profile.UpdatePhoneRespons
import com.ummug.mobilebank.domain.entity.profile.UpdateRequest
import retrofit2.Response
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val dataSource: CardsDataSource
) : CardsRepository {
    override suspend fun addCards(addCardEntity: AddCardEntity, bearerToken: String): Response<CardResponse> {
        return dataSource.addCard(addCardEntity,bearerToken)
    }

    override suspend fun getCards(bearerToken: String): GetCardsesponse {
        return dataSource.getCards(bearerToken)
    }

    override suspend fun delete(id: String, bearerToken: String): Response<String> {
        return dataSource.delete(id,bearerToken)
    }

    override suspend fun update(
        cardNameUpdate: CardNameUpdate,
        id: String,
        bearerToken: String
    ): Response<CardResponse> {
        return dataSource.update(cardNameUpdate,id, bearerToken)
    }

    override suspend fun listHistory(bearerToken: String): Response<HistoryRsponse> {
        return dataSource.listHistory(bearerToken)
    }

    override suspend fun My_about(bearerToken: String): Response<My_about> {
        return dataSource.My_about(bearerToken)
    }

    override suspend fun Update_Fuul(
        updateRequest: UpdateRequest,
        bearerToken: String
    ): Response<String> {
        return dataSource.Update_Fuul(updateRequest, bearerToken)
    }

    override suspend fun Update_parol(
        updatePaswordRequest: UpdatePaswordRequest,
        bearerToken: String
    ): Response<String> {
        return dataSource.Update_parol(updatePaswordRequest, bearerToken)
    }

    override suspend fun Update_phone(
        updatePhone: UpdatePhone,
        bearerToken: String
    ): Response<UpdatePhoneRespons> {
        return dataSource.Update_phone(updatePhone, bearerToken)
    }

    override suspend fun Update_phone_very(
        updatePhoneRespons: UpdatePhoneRespons,
        bearerToken: String
    ): Response<String> {
        return dataSource.Update_phone_very(updatePhoneRespons, bearerToken)
    }

}