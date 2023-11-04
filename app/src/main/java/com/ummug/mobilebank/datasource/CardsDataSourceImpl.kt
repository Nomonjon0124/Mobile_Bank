package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.data.api.AuthApi
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import javax.inject.Inject

class CardsDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
):CardsDataSource {
    override suspend fun getCards(bearerToken: String): GetCardsesponse {
        return authApi.getCards(bearerToken)
    }

    override suspend fun addCard(addCardEntity: AddCardEntity, bearerToken: String): Response<CardResponse> {
        return authApi.addCard(addCardEntity,bearerToken)
    }

}