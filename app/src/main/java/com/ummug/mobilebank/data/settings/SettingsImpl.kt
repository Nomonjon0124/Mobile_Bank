package com.ummug.mobilebank.data.settings

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
class SettingsImpl @Inject constructor(@ApplicationContext context: Context) : Settings {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override var temporaryToken: String?
        get() = preferences.getString("temp_token", null)
        set(value) = preferences.edit().putString("temp_token", value).apply()
    override var code: String?
        get() = preferences.getString("code", null)
        set(value) = preferences.edit().putString("code", value).apply()

    override var usetoken: String?
        get() = preferences.getString("usetoken",null)
        set(value) =preferences.edit().putString("usetoken", value).apply()

    override var transfercode: String?
        get() = preferences.getString("transfercode",null)
        set(value) =preferences.edit().putString("transfercode", value).apply()

    override var transfertoken: String?
        get() = preferences.getString("transfertoken",null)
        set(value) =preferences.edit().putString("transfertoken", value).apply()
}