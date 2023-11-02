package com.ummug.mobilebank.data.repository.Profile

import com.ummug.mobilebank.datasource.CardsDataSource
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val cardsDataSource: CardsDataSource
) :CardsRepository{
    override suspend fun getCards(bearerToken: String): Response<GetCardsesponse> {
        return cardsDataSource.getCards(bearerToken)
    }

    override suspend fun addCrad(
        addCardEntity: AddCardEntity,
        bearerToken: String
    ): Response<CardResponse> {
        return cardsDataSource.addCard(addCardEntity,bearerToken)
    }

}