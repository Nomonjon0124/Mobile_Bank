package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.profile.UpdatePhone
import com.ummug.mobilebank.domain.entity.profile.UpdatePhoneRespons
import java.io.IOException
import javax.inject.Inject

class UpdatePhoneUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {

    suspend operator fun invoke(phone:String?): State {

        if (phone==null || phone.length!=13) return State.Error(ErrorCodes.PHONE_NUMBER)

        try {
            val respons =
                cardsRepository.Update_phone(UpdatePhone(phone), "Bearer ${settings.usetoken}")
            cardsRepository.transfercode=respons.body()?.code
            cardsRepository.transferToken=respons.body()?.token

            cardsRepository.Update_phone_very(UpdatePhoneRespons(respons.body()?.code.toString(),respons.body()?.token.toString()),"Bearer ${settings.usetoken}")

            if (respons.code()==400 || respons.code()==404 || respons.code()==422 || respons.code()==406  ){
                return return State.Error(ErrorCodes.PHONE_NUMBER)
            }

        }catch (e:Exception){
            if (e is IOException){
                return  State.NoNetwork
            }
        }

        return State.Success(cardsRepository.transfercode)
    }
}