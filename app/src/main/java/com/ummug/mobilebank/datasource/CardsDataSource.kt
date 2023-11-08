package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.CardNameUpdate
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import com.ummug.mobilebank.domain.entity.transfer.TransferEntity
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

interface CardsDataSource {



   var transferToken: String?
   var transfercode: String?
   suspend fun getCards(bearerToken:String):GetCardsesponse

   suspend fun addCard(@Body addCardEntity: AddCardEntity, bearerToken:String):Response<CardResponse>

   suspend fun delete(id:String,bearerToken: String):Response<String>

   suspend fun update(cardNameUpdate: CardNameUpdate, id:String, bearerToken: String):Response<CardResponse>

   suspend fun transferMoney(transferEntity: TransferEntity,bearerToken: String):Response<TransferRespons>

   suspend fun transferVerify(transferRespons: TransferRespons,bearerToken: String):Response<String>
}