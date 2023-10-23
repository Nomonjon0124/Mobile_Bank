package com.ummug.mobilebank.di

import com.ummug.mobilebank.data.repository.VerificationRepository.verificationRepository
import com.ummug.mobilebank.data.repository.VerificationRepository.verificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VerificationRepositoryModul {

    @Singleton
    @Binds
    fun provideMainRepositoryImpl(repository: verificationRepositoryImpl): verificationRepository
}