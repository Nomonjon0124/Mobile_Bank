package com.ummug.mobilebank.di

import android.content.Context
import com.ummug.mobilebank.datasource.UsersDataSource
import com.ummug.mobilebank.datasource.UsersDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModul {


//    @Singleton
//    @Provides
//    fun provideApplicationContext(
//        @ApplicationContext context: Context
//    ) = context

    @Singleton
    @Binds
    fun provideMainRepositoryImpl(dataSource: UsersDataSourceImpl): UsersDataSource

}