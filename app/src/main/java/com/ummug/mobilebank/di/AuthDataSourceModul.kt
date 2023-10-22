package com.ummug.mobilebank.di

import com.ummug.mobilebank.datasource.AuthDataSource
import com.ummug.mobilebank.datasource.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface AuthDataSourceModul {
    @Binds
    fun bindAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource
}