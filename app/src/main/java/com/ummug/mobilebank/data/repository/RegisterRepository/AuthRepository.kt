package com.ummug.mobilebank.data.repository.RegisterRepository

import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse

interface AuthRepository {
    var temporaryToken: String?
    var code: String?
    var useToken:String?

    suspend fun signUp(signUpEntity: SignUpEntity): SignUpResponse
    suspend fun getUseToken(signUpResponse: SignUpResponse):String

    suspend fun signIn(signInEntity: SignInEntity): SignUpResponse


}