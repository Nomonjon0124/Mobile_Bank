package com.ummug.mobilebank.data.repository.Profile

import com.ummug.mobilebank.datasource.CardsDataSource
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val dataSource: CardsDataSource
) :CardsRepository{
    override suspend fun addCards(addCardEntity: AddCardEntity, bearerToken: String): Response<CardResponse> {
        return dataSource.addCard(addCardEntity,bearerToken)
    }

    override suspend fun getCards(bearerToken: String): GetCardsesponse {
        return dataSource.getCards(bearerToken)
    }

}