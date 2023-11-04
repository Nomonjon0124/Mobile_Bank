package com.ummug.mobilebank.data.repository.Profile

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import retrofit2.http.Body

interface CardsRepository {

    suspend fun addCards(@Body addCardEntity: AddCardEntity, bearerToken:String): Response<CardResponse>

    suspend fun getCards( bearerToken:String): GetCardsesponse

}