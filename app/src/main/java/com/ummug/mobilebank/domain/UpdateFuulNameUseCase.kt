package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.profile.UpdateRequest
import java.io.IOException
import javax.inject.Inject

class UpdateFuulNameUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {

    suspend operator fun invoke(firstName:String?, lastName:String?):State{
        if (firstName==null || firstName.length<3)return State.Error(ErrorCodes.FIRST_NAME_ERROR)
        if (lastName==null || lastName.length<3)return State.Error(ErrorCodes.LAST_NAME_ERROR)

        try {
            cardsRepository.Update_Fuul(UpdateRequest(firstName,lastName),"Bearer ${settings.usetoken}")
        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
        }
        return State.Success(Unit)
    }
}