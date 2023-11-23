package com.ummug.mobilebank.data.api

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.ResndCode
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignInResend
import com.ummug.mobilebank.domain.entity.SignInResponse
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import com.ummug.mobilebank.domain.CardNameUpdate
import com.ummug.mobilebank.domain.entity.History.HistoryRsponse
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import com.ummug.mobilebank.domain.entity.profile.My_about
import com.ummug.mobilebank.domain.entity.profile.UpdatePaswordRequest
import com.ummug.mobilebank.domain.entity.profile.UpdatePhone
import com.ummug.mobilebank.domain.entity.profile.UpdatePhoneRespons
import com.ummug.mobilebank.domain.entity.profile.UpdateRequest
import com.ummug.mobilebank.domain.entity.transfer.TransferEntity
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {

    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpEntity: SignUpEntity): SignUpResponse
    @POST("auth/sign-up/verify")
    suspend fun getUseToken(@Body signUpResponse: SignUpResponse): Response<SignInResponse>
    @POST("auth/sign-in")
    suspend fun SignIn(@Body signInEntity: SignInEntity): Response<SignInResponse>

    @POST("auth/sign-up/resend")
    suspend fun Resend(@Body resndCode: ResndCode):Response<SignInResend>


    @GET("cards")
    suspend fun getCards(@Header("Authorization") bearerToken :String): GetCardsesponse

    @POST("cards")
    suspend fun addCard(@Body cardData: AddCardEntity,  @Header("Authorization") bearerToken:String):Response<CardResponse>

    @DELETE("cards/{cardId}")
    suspend fun deleteCard(@Path("cardId") cardId: String, @Header("Authorization") bearerToken: String):Response<String>


    @PUT("cards/{cardId}")
    suspend fun updateCard(
        @Path("cardId") cardId: String,
        @Body cardNameUpdate: CardNameUpdate,
        @Header("Authorization") bearerToken: String
    ): Response<CardResponse>

    @POST("transfers")
    suspend fun transferMoney(
        @Body transferEntity: TransferEntity, @Header("Authorization") bearerToken: String
    ):Response<TransferRespons>

    @POST("transfers/verify")
    suspend fun verifyTransfer(
        @Body transferRespons: TransferRespons,@Header("Authorization") bearerToken: String
    ):Response<String>

    @GET("history")
    suspend fun listhistory(@Header("Authorization") bearerToken: String):Response<HistoryRsponse>

    @GET
    suspend fun Account_about(@Header("Authorization") bearerToken: String):Response<My_about>

    @POST
    suspend fun UpdateFullName(@Body updateRequest: UpdateRequest,@Header("Authorization") bearerToken: String):Response<String>

    @POST
    suspend fun UpdatePasword(@Body updatePaswordRequest: UpdatePaswordRequest,@Header("Authorization") bearerToken: String):Response<String>

    @POST
    suspend fun UpdatePhone(@Body updatePhone: UpdatePhone,@Header("Authorization") bearerToken: String):Response<UpdatePhoneRespons>
    @POST
    suspend fun UpdatePhoneVerify(@Body updatePhoneRespons: UpdatePhoneRespons,@Header("Authorization") bearerToken: String):Response<String>




}