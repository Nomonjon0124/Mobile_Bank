package com.ummug.mobilebank.domain

import android.annotation.SuppressLint
import android.util.Log
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.RegisterRepository.AuthRepository
import com.ummug.mobilebank.domain.entity.SignUpResponse
import java.io.IOException
import javax.inject.Inject

class SignVerifiUseToken @Inject constructor(private val authRepository: AuthRepository) {
    @SuppressLint("SuspiciousIndentation")
    suspend operator fun invoke(token:String?, code:String?): State {
        if (code?.length != 6) return State.Error(ErrorCodes.CODE_ERROR)
        if (token==null)return State.Error(ErrorCodes.CODE_ERROR)


            val entity = SignUpResponse(token,code)
            val response = authRepository.getUseToken(entity)
        if (response.isSuccessful){
            authRepository.useToken = response.body()?.access_token
        }
        else{
            return State.Error(ErrorCodes.CODE_ERROR)
        }


        return State.Success(authRepository.useToken)
    }
}