package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.CardNameUpdate
import com.ummug.mobilebank.domain.entity.History.HistoryRsponse
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import com.ummug.mobilebank.domain.entity.pay.payment
import com.ummug.mobilebank.domain.entity.profile.My_about
import com.ummug.mobilebank.domain.entity.profile.UpdatePaswordRequest
import com.ummug.mobilebank.domain.entity.profile.UpdatePhone
import com.ummug.mobilebank.domain.entity.profile.UpdatePhoneRespons
import com.ummug.mobilebank.domain.entity.profile.UpdateRequest
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

   suspend fun listHistory(bearerToken: String):Response<HistoryRsponse>

   suspend fun My_about(bearerToken: String):Response<My_about>

   suspend fun Update_Fuul(updateRequest: UpdateRequest,bearerToken: String):Response<String>

   suspend fun Update_parol(updatePaswordRequest: UpdatePaswordRequest,bearerToken: String):Response<String>

   suspend fun Update_phone(updatePhone: UpdatePhone,bearerToken: String):Response<UpdatePhoneRespons>

   suspend fun    Update_phone_very(updatePhoneRespons: UpdatePhoneRespons,bearerToken: String):Response<String>

   suspend fun PaymentAmount(payment: payment,bearerToken: String):Response<UpdatePhoneRespons>
   suspend fun PaymentVerify(updatePhoneRespons: UpdatePhoneRespons,bearerToken: String):Response<String>
}