package com.ummug.mobilebank.data.repository.VerificationRepository

import com.ummug.mobilebank.datasource.AuthDataSource
import com.ummug.mobilebank.domain.entity.SignUpResponse
import javax.inject.Inject

class verificationRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : verificationRepository {
    override suspend fun getUseToken(signUpResponse: SignUpResponse): String {
        return authDataSource.getUseToken(signUpResponse)
    }

    override var usetoken: String?
        get() = authDataSource.usetoken
        set(value) {
            authDataSource.usetoken=value
        }
}