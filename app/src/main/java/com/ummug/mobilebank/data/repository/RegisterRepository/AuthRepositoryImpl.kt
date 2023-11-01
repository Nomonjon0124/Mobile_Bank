package com.ummug.mobilebank.data.repository.RegisterRepository

import com.ummug.mobilebank.datasource.AuthDataSource
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

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
    override var useToken: String?
        get() = authDataSource.usetoken
        set(value) {
            authDataSource.usetoken=value
        }

    override suspend fun signUp(signUpEntity: SignUpEntity): SignUpResponse {
        return authDataSource.signUp(signUpEntity)
    }

    override suspend fun signIn(signInEntity: SignInEntity): SignUpResponse {
        return authDataSource.signIn(signInEntity)
    }

    override suspend fun getUseToken(signUpResponse: SignUpResponse): String {
        return authDataSource.getUseToken(signUpResponse)
    }
}