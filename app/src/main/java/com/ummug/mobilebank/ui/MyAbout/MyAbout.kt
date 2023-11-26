package com.ummug.mobilebank.ui.MyAbout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyAbout : Fragment(R.layout.fragment_about) {

    private val viewModel:MyAboutViewModel  by viewModels ()
    private val binding:FragmentAboutBinding by viewBinding ()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.MyAbout()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.noNetworkFlow.collect {
                    Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openAboutFlow.collect {
                    binding.id.text=it.id.toString()
                    binding.firstName.text=it.first_name
                    binding.lastName.text=it.last_name
                    binding.phone.text=it.phone_number
                    binding.phoneNumberVerified.text=it.phone_number_verified.toString()
                    binding.createdAt.text=it.created_at
                    binding.updatedAt.text=it.updated_at
                }
            }
        }
    }
    }
