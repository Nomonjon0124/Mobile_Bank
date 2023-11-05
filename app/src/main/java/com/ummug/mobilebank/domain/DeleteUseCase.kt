package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.Profile.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import java.io.IOException
import javax.inject.Inject

class DeleteUseCase @Inject constructor(private val cardsRepository: CardsRepository,private val settings: Settings) {

    private var message:String="Error"
    suspend operator fun invoke(id:String):State{

        try {
            val response = cardsRepository.delete(id, "Bearer ${settings.usetoken}")
            if (response.isSuccessful){
                message=response.message()
            }
        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
        }
        return State.Success(Unit)
    }
}