package com.ummug.mobilebank.di

import com.ummug.mobilebank.data.repository.RegisterRepository.registerRepository
import com.ummug.mobilebank.data.repository.RegisterRepository.registerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RegisterRepositoryModul {

    @Singleton
    @Binds
    fun provideMainRepositoryImpl(repository: registerRepositoryImpl): registerRepository
}