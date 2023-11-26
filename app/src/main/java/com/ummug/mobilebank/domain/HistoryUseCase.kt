package com.ummug.mobilebank.domain

import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.ummug.mobilebank.App
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.domain.entity.History.Data
import com.ummug.mobilebank.domain.entity.History.HistoryRsponse
import java.io.IOException
import javax.inject.Inject

class HistoryUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: com.ummug.mobilebank.data.settings.Settings
) {
    suspend operator fun invoke():State{
        try {
            val response = cardsRepository.listHistory("Bearer ${settings.usetoken}")

            return State.Success(response.body())
        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
            return State.Error(1)
        }

    }

}