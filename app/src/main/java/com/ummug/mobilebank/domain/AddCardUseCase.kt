package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.Profile.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.AddCardEntity
import java.io.IOException
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val repository: CardsRepository,private val settings: Settings
) {

    private var messege:String=""
    suspend operator fun invoke(addCardEntity: AddCardEntity):State{
        if (addCardEntity.pan.length!=16) return State.Error(6)
        if (addCardEntity.name.length<3)return State.Error(7)
        if (addCardEntity.expire_year<2023) return State.Error(8)
        if (addCardEntity.expire_month<1||addCardEntity.expire_month>12) return State.Error(9)

        try {
            val response = repository.addCards(addCardEntity, "Bearer ${settings.usetoken}")
            val successful = response.isSuccessful
            if (!successful){
                if (response.code()==422||response.code()==400){
                    messege=response.message()
                    return State.Success(messege)
                }

            }


        }catch (e:Exception){
            e.printStackTrace()
            if(e is IOException) return State.NoNetwork

            return State.Error(5)
        }
        return State.Success(messege)

    }
}