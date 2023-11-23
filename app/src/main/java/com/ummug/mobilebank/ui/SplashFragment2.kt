package com.ummug.mobilebank.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentSplash2Binding
import com.ummug.mobilebank.ui.Login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

            bottom.setOnClickListener {
                if (checkbox.isChecked){
                    findNavController().navigate(R.id.action_splashFragment2_to_loginFragment)
                }
                else{
                    Toast.makeText(requireContext(), "Oferaviy shatrlarga rozimizsiz", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}