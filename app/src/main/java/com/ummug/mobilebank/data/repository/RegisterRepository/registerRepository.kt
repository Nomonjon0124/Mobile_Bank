package com.ummug.mobilebank.data.repository.RegisterRepository

import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse

interface registerRepository {

    var temporaryToken: String?
    var code: String?

    suspend fun signUp(signUpEntity: SignUpEntity): SignUpResponse

}