package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.Transfer.TransferRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import java.io.IOException
import javax.inject.Inject

class TransferMoneyVery @Inject constructor(
    private val transferRepository: TransferRepository,
    private val settings: Settings


) {
    suspend operator fun invoke(code:String?):State{

        if (code==null || code.isNullOrEmpty() || code!=settings.transfercode)return State.Error(ErrorCodes.CODE_ERROR)

        try {
            transferRepository.verifyTransfer(TransferRespons(code,transferRepository.transferToken.toString()),"Bearer ${settings.usetoken}")
        }
        catch (e : Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
        }

        return State.Success(Unit)

    }
}