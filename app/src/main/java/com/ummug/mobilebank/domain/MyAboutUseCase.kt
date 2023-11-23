package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import java.io.IOException
import javax.inject.Inject

class MyAboutUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {
    private var message=""
    suspend operator fun invoke():State{
        try {
            val myAbout = cardsRepository.My_about("Bearer ${settings.usetoken}")
            message=myAbout.message()
        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
        }
        return State.Success(message)
    }
}