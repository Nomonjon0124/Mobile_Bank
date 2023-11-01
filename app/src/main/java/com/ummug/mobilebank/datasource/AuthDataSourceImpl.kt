package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.data.api.AuthApi
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi, private val settings: Settings
) :AuthDataSource {
    override var temporaryToken: String?
        get() = settings.temporaryToken
        set(value) {
            settings.temporaryToken = value
        }
    override var code: String?
        get() = settings.code
        set(value) {
            settings.code = value
        }
    override var usetoken: String?
        get() = settings.usetoken
        set(value) {
            settings.usetoken=value
        }


    override suspend fun signUp(signUpEntity: SignUpEntity): SignUpResponse {
        return authApi.signUp(signUpEntity)
    }

    override suspend fun getUseToken(signUpResponse: SignUpResponse): String {
        return authApi.getUseToken(signUpResponse)
    }

    override suspend fun signIn(signInEntity: SignInEntity): SignUpResponse {
        return authApi.SignIn(signInEntity)
    }


}
