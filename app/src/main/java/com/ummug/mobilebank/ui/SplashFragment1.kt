package com.ummug.mobilebank.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ummug.mobilebank.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment1 : Fragment(R.layout.fragment_splash1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        object : CountDownTimer(500, 1000) {
            override fun onTick(p0: Long) {

            }
            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment1_to_splashFragment2)
            }

        }.start()
    }
}