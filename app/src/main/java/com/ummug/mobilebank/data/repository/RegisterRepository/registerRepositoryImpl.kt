package com.ummug.mobilebank.data.repository.RegisterRepository

import com.ummug.mobilebank.datasource.AuthDataSource
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import javax.inject.Inject

class registerRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : registerRepository {

    override var temporaryToken: String?
        get() = authDataSource.temporaryToken
        set(value) {
            authDataSource.temporaryToken = value
        }
    override var code: String?
        get() = authDataSource.code
        set(value) {
            authDataSource.code = value
        }

    override suspend fun signUp(signUpEntity: SignUpEntity): SignUpResponse {
        return authDataSource.signUp(signUpEntity)
    }

}