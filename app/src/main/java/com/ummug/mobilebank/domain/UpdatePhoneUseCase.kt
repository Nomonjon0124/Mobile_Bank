package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.profile.UpdatePhone
import java.io.IOException
import javax.inject.Inject

class UpdatePhoneUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {

    suspend operator fun invoke(phone:String?): State {

        if (phone==null || phone.length!=13) return State.Error(ErrorCodes.PHONE_NUMBER)

        try {
            val updatePhone =
                cardsRepository.Update_phone(UpdatePhone(phone), "Bearer ${settings.usetoken}")
            settings.transfercode=updatePhone.body()?.code
            settings.transfertoken=updatePhone.body()?.token

        }catch (e:Exception){
            if (e is IOException){
                return  State.NoNetwork
            }
        }

        return State.Success(Unit)
    }
}