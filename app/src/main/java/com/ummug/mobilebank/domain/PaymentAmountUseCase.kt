package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.domain.entity.pay.payment
import com.ummug.mobilebank.domain.entity.profile.UpdatePhoneRespons
import java.io.IOException
import javax.inject.Inject

class PaymentAmountUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: com.ummug.mobilebank.data.settings.Settings
) {

    private var message:String=""
    suspend operator fun invoke(amount:String?,phone:String?,cardIs:Int?):State{

        if (amount==null || amount.isNullOrEmpty() || amount.toInt()<1000)return State.Error(ErrorCodes.MONEY)
        if (phone==null || phone.length<13 )return State.Error(ErrorCodes.PHONE_NUMBER)

        try {
            val response = cardsRepository.PaymentAmount(
                payment(amount.toInt(), cardIs!!, 2, phone),
                "Bearer ${settings.usetoken}"
            )
           val d = response.body() as UpdatePhoneRespons
            cardsRepository.transfercode=d.code
            cardsRepository.transferToken=d.token

            if (response.isSuccessful){
                message=response.message()
            }else{
                message=response.errorBody().toString()
            }
        }
        catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
        }

        return State.Success(cardsRepository.transfercode)

    }
}