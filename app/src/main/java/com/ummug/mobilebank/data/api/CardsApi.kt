package com.ummug.mobilebank.data.api

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CardsApi {

    @GET("cards")
    suspend fun getCards(@Header("Authorization") bearerToken: String): Response<GetCardsesponse>

    @POST("cards")
    suspend fun addCard(@Body cardData: AddCardEntity, @Header("Authorization") bearerToken: String):Response<CardResponse>

    @DELETE("cards/{cardId}")
    suspend fun deleteCard(@Path("cardId") cardId: String, @Header("Authorization") bearerToken: String)
}