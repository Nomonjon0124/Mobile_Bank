package com.ummug.mobilebank.di

import com.ummug.mobilebank.data.repository.HomeRepository.homeRepository
import com.ummug.mobilebank.data.repository.HomeRepository.homeRepositoryImpl
import com.ummug.mobilebank.data.repository.LoginRepository.loginRepository
import com.ummug.mobilebank.data.repository.LoginRepository.loginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LoginModul {

    @Singleton
    @Binds
    fun provideMainRepositoryImpl(repository: loginRepositoryImpl): loginRepository
}