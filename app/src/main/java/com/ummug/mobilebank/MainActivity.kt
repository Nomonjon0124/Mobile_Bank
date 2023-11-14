package com.ummug.mobilebank

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ummug.mobilebank.data.settings.Preferens
import com.ummug.mobilebank.ui.PinFragment
import com.ummug.mobilebank.ui.SplashFragment1
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var preferens: Preferens

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    private var timer: Timer? = null
    private var task: TimerTask? = null
    private val delayInMillis: Long = 1 * 10 * 1000
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStop() {
         timer = Timer()
         task = object : TimerTask() {
            override fun run() {
                Log.d("sss", "run: ishladi")
                finish()
            }
        }
        val scheduledTime = Date(System.currentTimeMillis() + delayInMillis)
        timer!!.schedule(task, scheduledTime)
        Log.d("sss", "stop ishladi: ")
        super.onStop()
    }
    override fun onStart() {
        timer?.cancel()
        Log.d("sss", "onStart: ")
        super.onStart()
    }


}