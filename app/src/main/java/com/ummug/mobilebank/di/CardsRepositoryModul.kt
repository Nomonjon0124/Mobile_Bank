package com.ummug.mobilebank.di

import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.repository.CardsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CardsRepositoryModul {

    @Singleton
    @Binds
    fun provideCardsRepositoryImpl(cardsRepositoryImpl: CardsRepositoryImpl): CardsRepository
}