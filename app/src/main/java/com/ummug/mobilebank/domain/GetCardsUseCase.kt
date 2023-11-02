package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.repository.Profile.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.cards.GetCardsesponse
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {

    suspend operator fun invoke():GetCardsesponse{
        val response=cardsRepository.getCards(settings.usetoken.toString())
        return response
    }
}