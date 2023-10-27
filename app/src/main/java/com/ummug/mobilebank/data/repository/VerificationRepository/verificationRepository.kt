package com.ummug.mobilebank.data.repository.VerificationRepository

import com.ummug.mobilebank.domain.entity.SignUpResponse

interface  verificationRepository {

    var usetoken: String?
    suspend fun getUseToken(signUpResponse: SignUpResponse):String
}