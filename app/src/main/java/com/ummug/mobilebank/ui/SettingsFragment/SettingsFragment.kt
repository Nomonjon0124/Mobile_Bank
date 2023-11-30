package com.ummug.mobilebank.ui.SettingsFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentSettingsBinding
import com.ummug.mobilebank.ui.AppInformationFragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding:FragmentSettingsBinding by viewBinding ()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appInformation.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_appInformationFragment)
        }
        binding.apply {
            passwordUpdate.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_updatePassword)
            }
            updateFullname.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_updateFullname)
            }
//            about.setOnClickListener {
//                findNavController().navigate(R.id.action_settingsFragment_to_myAbout)
//            }
            phoneUpdate.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_updatePhone)
            }

        }
    }
}