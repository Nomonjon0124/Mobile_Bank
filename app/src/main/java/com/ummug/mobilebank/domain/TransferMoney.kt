package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.repository.Transfer.TransferRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.transfer.TransferEntity
import com.ummug.mobilebank.domain.entity.transfer.TransferRespons
import java.io.IOException
import javax.inject.Inject

class TransferMoney @Inject constructor(
    private val transferRepository: TransferRepository,
    private val settings: Settings
) {

    private var message:String=""
    suspend operator fun invoke(from_card_id:Int,money:String?,pan:String?):State{

        if (money==null || money.isNullOrEmpty() || money.toInt()<5000)return State.Error(ErrorCodes.MONEY)
        if (pan==null || pan.length!=16 || pan.isNullOrEmpty())return State.Error(ErrorCodes.PEN_ERROR)

        try {
           val response= transferRepository.transferMoney(TransferEntity(money.toInt(),from_card_id,pan),"Bearer ${settings.usetoken}")
            val f=response.body() as TransferRespons
            transferRepository.transferToken=f.token
            transferRepository.transfercode=f.code

            transferRepository.verifyTransfer(TransferRespons(f.code,f.token),"Bearer ${settings.usetoken}")


            if (response.isSuccessful){
                message=response.message()
            }else{
                message=response.errorBody().toString()
            }
        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
        }
        return State.Success(transferRepository.transfercode)
    }
}