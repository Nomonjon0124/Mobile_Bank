package com.ummug.mobilebank.data.settings

import android.content.Context
import android.content.SharedPreferences

class Preferens (context: Context){

    private  var preferences: SharedPreferences =context.getSharedPreferences("settings",
        Context.MODE_PRIVATE)
    companion object{
        lateinit var  settings:Preferens
        fun getSettings(context: Context):Preferens{
            if (!::settings.isInitialized){
                settings=Preferens(context)
            }
            return settings
        }

    }

    fun setPincode(pincode:String){
        preferences.edit().putString("pin",pincode).apply()
    }
    fun getPincode():String?{
        return preferences.getString("pin","")
    }
}