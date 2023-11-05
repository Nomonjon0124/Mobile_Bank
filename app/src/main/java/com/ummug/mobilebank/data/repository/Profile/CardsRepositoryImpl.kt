package com.ummug.mobilebank.data.repository.Profile

import com.ummug.mobilebank.datasource.CardsDataSource
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.CardNameUpdate
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

}