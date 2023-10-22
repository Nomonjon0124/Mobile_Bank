package com.ummug.mobilebank.di

import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.data.settings.SettingsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CacheModule {
    @Binds
    fun bindSettings(settingsImpl: SettingsImpl): Settings
}