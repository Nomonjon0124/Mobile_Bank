package com.ummug.mobilebank.ui.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val registerFragmentViewModel:RegisterFragmentViewModel by viewModels()

    private var _binding: FragmentRegisterBinding?=null ;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            famalebox.setOnClickListener {
                malebox.isChecked=false
                if(famalebox.isChecked){
                    naxt.setBackgroundResource(R.color.button_color)
                }
                else if (!famalebox.isChecked){
                    naxt.setBackgroundResource(R.color.button_color_defoult)
                }
            }
            malebox.setOnClickListener {
                famalebox.isChecked=false
                if(malebox.isChecked){
                    naxt.setBackgroundResource(R.color.button_color)
                }
                else if (!malebox.isChecked){
                    naxt.setBackgroundResource(R.color.button_color_defoult)
                }
            }
        }

    }
}