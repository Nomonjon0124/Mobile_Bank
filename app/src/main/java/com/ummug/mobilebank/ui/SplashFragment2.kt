package com.ummug.mobilebank.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentSplash2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment2:Fragment(R.layout.fragment_splash2) {

    private var _binding:FragmentSplash2Binding?=null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val b=true
            checkbox.setOnClickListener {
                if (!b){
                    checkbox.setImageResource(R.drawable.sign_round2)
                    bottom.setCardBackgroundColor(Color.GREEN)
                }
                else{
                    checkbox.setImageResource(R.drawable.sign_round)
                    bottom.setCardBackgroundColor(Color.WHITE)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}