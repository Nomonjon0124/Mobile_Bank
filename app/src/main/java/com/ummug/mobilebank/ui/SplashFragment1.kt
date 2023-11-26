package com.ummug.mobilebank.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.settings.Preferens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment1 : Fragment(R.layout.fragment_splash1) {


    private lateinit var preferens: Preferens

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        object : CountDownTimer(500, 1000) {
            override fun onTick(p0: Long) {
            }
            override fun onFinish() {

            }
        }.start()
        preferens= Preferens.getSettings(requireContext())
        if (preferens.getPincode()?.isNullOrEmpty() == false){
            object : CountDownTimer(800, 1000) {
                override fun onTick(p0: Long) {
                }
                override fun onFinish() {
                    findNavController().navigate(R.id.action_splashFragment1_to_pinFragment)

                }
            }.start()

        }
        else {
            object : CountDownTimer(800, 1000) {
                override fun onTick(p0: Long) {
                }
                override fun onFinish() {
                    findNavController().navigate(R.id.action_splashFragment1_to_splashFragment2)
                }
            }.start()

        }
    }

}