package com.ummug.mobilebank.domain

import android.annotation.SuppressLint
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import java.io.IOException
import javax.inject.Inject

class UpdateUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {

    private lateinit var body: CardResponse

    @SuppressLint("SuspiciousIndentation")
    suspend operator fun invoke(name:String?, theme:Int, id:String):State{


        if (name==null || name.length<3)return State.Error(ErrorCodes.CARD_NAME)
        ErrorCodes
        try {
            val response = cardsRepository.update(CardNameUpdate(name,theme), id, "Bearer ${settings.usetoken}")
               return State.Success(response.body())

        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
            return State.Error(7)
        }
    }
}