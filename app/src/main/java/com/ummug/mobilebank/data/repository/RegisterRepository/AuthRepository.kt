package com.ummug.mobilebank.data.repository.RegisterRepository

import com.ummug.mobilebank.domain.entity.ResndCode
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignInResend
import com.ummug.mobilebank.domain.entity.SignInResponse
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import retrofit2.Response

interface AuthRepository {
    var temporaryToken: String?
    var code: String?
    var useToken:String?

    suspend fun signUp(signUpEntity: SignUpEntity): SignUpResponse
    suspend fun getUseToken(signUpResponse: SignUpResponse):Response<SignInResponse>

    suspend fun signIn(signInEntity: SignInEntity): Response<SignInResponse>

    suspend fun resendToken(resndCode: ResndCode):Response<SignInResend>


}