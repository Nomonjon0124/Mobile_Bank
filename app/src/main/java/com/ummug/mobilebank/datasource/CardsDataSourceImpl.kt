package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.data.api.CardsApi
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import javax.inject.Inject

class CardsDataSourceImpl @Inject constructor(
    private val cardsApi: CardsApi
):CardsDataSource {
    override suspend fun getCards(bearerToken: String): Response<GetCardsesponse> {
        return cardsApi.getCards(bearerToken)
    }

    override suspend fun addCard(
        cardData: AddCardEntity,
        bearerToken: String
    ): Response<CardResponse> {
        return cardsApi.addCard(cardData,bearerToken)
    }

}