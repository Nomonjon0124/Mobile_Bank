package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.Transfer.TransferRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import java.io.IOException
import javax.inject.Inject

class VarifyTransfer @Inject constructor(
    private val transferRepository: TransferRepository,
    private val settings: Settings
) {

    private var message:String=""
    suspend operator fun invoke():State{

        try {
            val respons=transferRepository.verifyTransfer(TransferRespons(settings.transfercode.toString(),settings.transfertoken.toString()),"Bearer ${settings.usetoken}")
            message=respons.message()
        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
        }
        return State.Success(message)
    }
}