package com.ummug.mobilebank.data.api

import com.ummug.mobilebank.domain.entity.ListCards
import com.ummug.mobilebank.domain.entity.ResndCode
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignInResend
import com.ummug.mobilebank.domain.entity.SignInResponse
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpEntity: SignUpEntity): SignUpResponse
    @POST("auth/sign-up/verify")
    suspend fun getUseToken(@Body signUpResponse: SignUpResponse): Response<SignInResponse>
    @POST("auth/sign-in")
    suspend fun SignIn(@Body signInEntity: SignInEntity): Response<SignInResponse>

    @POST("auth/sign-up/resend")
    suspend fun Resend(@Body resndCode: ResndCode):Response<SignInResend>

    @GET
    suspend fun Profile(@Body bearer_Token: ListCards)


}