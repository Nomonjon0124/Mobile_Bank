package com.ummug.mobilebank.domain

import android.util.Log
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.RegisterRepository.AuthRepository
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignUpEntity
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(  phone: String?,password: String?): State {


        if (password == null || password.length < 4) return State.Error(ErrorCodes.PASSWORD)
        if (phone == null || phone.length != 13) return State.Error(ErrorCodes.PHONE_NUMBER)

        try {
            val entity = SignInEntity(phone, password)

            val response = authRepository.signIn(entity)
            Log.d("tag", response.code+response.token)
            authRepository.temporaryToken = response.token
            authRepository.code = response.code

        } catch (exception: Exception) {
            exception.printStackTrace()
            if (exception is IOException) return State.NoNetwork
            return State.Error(1)
        }
        return State.Success(authRepository.code+authRepository.temporaryToken)
    }


}