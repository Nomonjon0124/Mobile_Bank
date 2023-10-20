package com.ummug.mobilebank.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import com.ummug.mobilebank.R

class SplashFragment1: Fragment(R.layout.fragment_splash1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        object :CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.container,SplashFragment2())
                    .commit()
            }

            override fun onFinish() {

            }

        }
    }
}