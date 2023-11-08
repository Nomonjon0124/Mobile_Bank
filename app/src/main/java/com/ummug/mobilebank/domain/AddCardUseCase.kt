package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.AddCardEntity
import java.io.IOException
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val repository: CardsRepository,private val settings: Settings
) {

    private var messege:String=""
    suspend operator fun invoke(pan:String?,cardname:String?,year:Int?,month:Int?):State{

        if (pan==null || pan.length != 16) return State.Error(ErrorCodes.PEN_ERROR)
        if (cardname==null || cardname.length<3)return State.Error(ErrorCodes.CARD_NAME)
        if (year==null || year<2023) return State.Error(ErrorCodes.YEAR)
        if (month==null || month<1||month>12) return State.Error(ErrorCodes.MONH)

        try {
            val response = repository.addCards(AddCardEntity(month,year, cardname,pan), "Bearer ${settings.usetoken}")
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