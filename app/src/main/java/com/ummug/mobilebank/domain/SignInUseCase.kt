package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.RegisterRepository.AuthRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.SignInEntity
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authRepository: AuthRepository,private val settings: Settings) {
    suspend operator fun invoke(  phone: String?,password: String?): State {

        if (password == null || password.length < 4) return State.Error(ErrorCodes.PASSWORD)
        if (phone == null || phone.length != 13) return State.Error(ErrorCodes.PHONE_NUMBER)

        try {
            val entity = SignInEntity(phone, password)
            val response = authRepository.signIn(entity)
            if (response.isSuccessful)
           authRepository.useToken=response.body()?.access_token
            else{
                return State.Error(ErrorCodes.PHONE_NUMBER,response.message())
            }
        }
        catch (exception: Exception) {
            exception.printStackTrace()
            if (exception is IOException) return State.NoNetwork
            return State.Error(1)
        }
        return State.Success(authRepository.useToken)
    }


}