package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import retrofit2.http.Header

interface CardsDataSource {

   suspend fun getCards(bearerToken: String):Response<GetCardsesponse>

   suspend fun addCard(cardData: AddCardEntity, bearerToken: String):Response<CardResponse>
}