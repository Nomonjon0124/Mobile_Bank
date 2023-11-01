package com.ummug.mobilebank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ummug.mobilebank.ui.Home.HomeFragment
import com.ummug.mobilebank.ui.SplashFragment1
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.container,SplashFragment1())
            .commit()
    }
}