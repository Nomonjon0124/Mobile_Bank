package com.ummug.mobilebank.data.api

import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.domain.entity.ResndCode
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignInResend
import com.ummug.mobilebank.domain.entity.SignInResponse
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import com.ummug.mobilebank.domain.CardNameUpdate
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApi {

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


}