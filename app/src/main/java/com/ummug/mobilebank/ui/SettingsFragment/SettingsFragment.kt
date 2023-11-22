package com.ummug.mobilebank.ui.SettingsFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentSettingsBinding
import com.ummug.mobilebank.ui.AppInformationFragment
import com.ummug.mobilebank.ui.BaseFragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding:FragmentSettingsBinding by viewBinding ()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appInformation.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("SettingsFragment")
                .replace(R.id.container,AppInformationFragment())
                .commit()
        }
    }
}