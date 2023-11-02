package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.repository.Profile.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.AddCardEntity
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {
    suspend operator fun invoke(addCardEntity: AddCardEntity){

    }
}