package com.ummug.mobilebank.di

import com.ummug.mobilebank.datasource.AuthDataSource
import com.ummug.mobilebank.datasource.AuthDataSourceImpl
import com.ummug.mobilebank.datasource.CardsDataSource
import com.ummug.mobilebank.datasource.CardsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface CardsDataSourceModul {
    @Binds
    fun bindCardsDataSource(cardsDataSourceImpl: CardsDataSourceImpl): CardsDataSource
}