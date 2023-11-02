package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.RegisterRepository.AuthRepository
import com.ummug.mobilebank.domain.entity.ResndCode
import java.io.IOException
import javax.inject.Inject

class ResendUseCase @Inject constructor(
    private val authRepository: AuthRepository) {

    var errormessage:String=""
    suspend operator fun invoke(token:String):State{
        try {
            val entity=ResndCode(token)
            val resendToken = authRepository.resendToken(entity)
            val body = resendToken.body()
            errormessage=resendToken.errorBody().toString()
            if (resendToken.isSuccessful){
                authRepository.temporaryToken=body?.token
                authRepository.code=body?.code
            }
            else{
                return State.Error(ErrorCodes.PASSWORD)
            }
        }
        catch (e:Exception){
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            return State.Error(5)
        }
        return State.Success(authRepository.code)
    }
}