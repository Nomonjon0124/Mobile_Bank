package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.cardistory.cardHistory
import java.io.IOException
import javax.inject.Inject

class getHistoryForCard @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {

    suspend operator fun invoke(cardId:Int):State{

        try {
            val response =
                cardsRepository.getHistoryForCard(cardId, "Bearer ${settings.usetoken}")

            return State.Success(response.body() as cardHistory)
        }catch (e : Exception){
            if (e is IOException){
                return State.NoNetwork
            }
            return State.Error(1)
        }
    }
}