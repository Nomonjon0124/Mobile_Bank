package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.Profile.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import java.io.IOException
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val repository:CardsRepository,private val settings: Settings
) {

    private lateinit var getCard:GetCardsesponse

    suspend operator fun invoke():State{

        try {
            val cards = repository.getCards("Bearer ${settings.usetoken}")

            val data = cards.data
            return State.Success(cards.data)


        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            return State.Error(1)

        }
    }
}