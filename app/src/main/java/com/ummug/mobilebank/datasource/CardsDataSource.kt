package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

interface CardsDataSource {

   suspend fun getCards(bearerToken:String):GetCardsesponse

   suspend fun addCard(@Body addCardEntity: AddCardEntity, bearerToken:String):Response<CardResponse>
}