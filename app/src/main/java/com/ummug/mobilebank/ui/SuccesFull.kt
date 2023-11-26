package com.ummug.mobilebank.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentSuccessfullyBinding

class SuccesFull :Fragment(R.layout.fragment_successfully) {

    private val binding : FragmentSuccessfullyBinding by viewBinding ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.succesback.setOnClickListener {
            findNavController().navigate(R.id.action_succesFull_to_homeFragment)
        }
    }
}