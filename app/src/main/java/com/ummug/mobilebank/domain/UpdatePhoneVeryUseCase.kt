package com.ummug.mobilebank.domain

import android.provider.Settings
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.domain.entity.profile.UpdatePhoneRespons
import java.io.IOException
import javax.inject.Inject

class UpdatePhoneVeryUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: com.ummug.mobilebank.data.settings.Settings
) {

    suspend operator fun invoke(code:String?):State{

        if (code==null || code.length!=6 || code!=settings.transfercode)return State.Error(ErrorCodes.PASSWORD)

        try {

            cardsRepository.Update_phone_very(UpdatePhoneRespons(code,cardsRepository.transferToken.toString()),"Bearer ${settings.usetoken}")
        }catch (e:Exception){
            if (e is IOException){
                return  State.NoNetwork
            }
        }

        return State.Success(Unit)
    }
}