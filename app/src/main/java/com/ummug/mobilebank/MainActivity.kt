package com.ummug.mobilebank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ummug.mobilebank.data.settings.Preferens
import com.ummug.mobilebank.ui.PinFragment
import com.ummug.mobilebank.ui.SplashFragment1
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var preferens: Preferens
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragmnet_home)

        preferens=Preferens.getSettings(this)
        if (preferens.getPincode()?.isNullOrEmpty() == false){
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, PinFragment())
                .commit()
        }
        else {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, SplashFragment1())
                .commit()
        }

    }
}