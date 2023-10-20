package com.ummug.mobilebank.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentSplash2Binding

class SplashFragment2:Fragment(R.layout.fragment_splash2) {
    private var _binding: FragmentSplash2Binding?=null ;
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSplash2Binding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkbox.setOnClickListener {
                if(checkbox.isChecked){
                    bottom.setBackgroundResource(R.color.button_color)
                }
                else if (!checkbox.isChecked){
                    bottom.setBackgroundResource(R.color.button_color_defoult)
                }
            }
        }
    }
}