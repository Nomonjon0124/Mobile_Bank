package com.ummug.mobilebank.di

import com.ummug.mobilebank.data.repository.Transfer.TransferRepository
import com.ummug.mobilebank.data.repository.Transfer.TransferRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TransferModule {


    @Singleton
    @Binds
    fun bindsTransferRepo(transferRepositoryImpl: TransferRepositoryImpl):TransferRepository
}