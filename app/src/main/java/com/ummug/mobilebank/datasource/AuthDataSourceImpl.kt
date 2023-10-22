package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.data.api.AuthApi
import com.ummug.mobilebank.data.settings.Settings
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

    override suspend fun signUp(signUpEntity: SignUpEntity): SignUpResponse {
        return authApi.signUp(signUpEntity)

    }
    }
