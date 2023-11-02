package com.ummug.mobilebank.di

import com.ummug.mobilebank.data.repository.Profile.CardsRepository
import com.ummug.mobilebank.data.repository.Profile.CardsRepositoryImpl
import com.ummug.mobilebank.data.repository.RegisterRepository.AuthRepository
import com.ummug.mobilebank.data.repository.RegisterRepository.AuthRepositoryImpl
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