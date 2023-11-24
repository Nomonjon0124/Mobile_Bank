package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.profile.UpdatePaswordRequest
import java.io.IOException
import javax.inject.Inject

class UpdateParolUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {

    suspend operator fun invoke(currentparol:String?,parol:String?,parol_confirmation:String?):State{

        if (currentparol==null || currentparol.length<4)return State.Error(ErrorCodes.PASSWORD)
        if (parol==null || parol.length<4)return State.Error(ErrorCodes.CODE_ERROR)
        if (parol_confirmation==null || parol_confirmation.length<4 || parol!=parol_confirmation)return State.Error(ErrorCodes.CODE_ERROR)

        try {
            cardsRepository.Update_parol(UpdatePaswordRequest(currentparol,parol,parol_confirmation),"Bearer ${settings.usetoken}")
         }catch (e:Exception){
            if (e is IOException){
                return State.NoNetwork
            }
        }
        return State.Success(Unit)

    }
}