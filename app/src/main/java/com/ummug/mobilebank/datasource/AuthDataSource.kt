package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse

interface AuthDataSource {

     var temporaryToken: String?
     var code: String?

     suspend fun signUp(signUpEntity: SignUpEntity): SignUpResponse
}