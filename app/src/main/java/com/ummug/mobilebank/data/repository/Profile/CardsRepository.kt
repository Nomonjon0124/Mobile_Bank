package com.ummug.mobilebank.data.repository.Profile

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response

interface CardsRepository {

    suspend fun getCards(bearerToken: String):Response<GetCardsesponse>

    suspend fun addCrad(addCardEntity: AddCardEntity,bearerToken: String):Response<CardResponse>

}