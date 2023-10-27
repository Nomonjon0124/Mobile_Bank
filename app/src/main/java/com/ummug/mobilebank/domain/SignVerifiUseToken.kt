package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.VerificationRepository.verificationRepository
import com.ummug.mobilebank.domain.entity.SignUpResponse
import java.io.IOException
import javax.inject.Inject

class SignVerifiUseToken @Inject constructor(private val verificationRepository: verificationRepository) {
    suspend operator fun invoke(code:String?,token:String?): State {
        if (code == null || code.length != 6) return State.Error(ErrorCodes.CODE_ERROR)
        try {
            val entity = SignUpResponse(code,token!!)
            val response = verificationRepository.getUseToken(entity)
            verificationRepository.usetoken = response
        } catch (exception: Exception) {
            exception.printStackTrace()
            if (exception is IOException) return State.NoNetwork
            return State.Error(1)
        }
        return State.Success(verificationRepository.usetoken)
    }
}