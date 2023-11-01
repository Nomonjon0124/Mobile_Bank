package com.ummug.mobilebank.data.api

import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpEntity: SignUpEntity): SignUpResponse

    @POST("auth/sign-up/verify")
    suspend fun getUseToken(@Body signUpResponse: SignUpResponse): String

    @POST("auth/sign-in")
    suspend fun SignIn(@Body signInEntity: SignInEntity): SignUpResponse
}