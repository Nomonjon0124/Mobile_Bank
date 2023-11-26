package com.ummug.mobilebank.ui.UpdatePhone

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
import com.ummug.mobilebank.databinding.FragmentPhoneUpdateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdatePhone :Fragment(R.layout.fragment_phone_update) {

    private val viewModel:UpdatePhoneViewModel by viewModels ()
    private val binding:FragmentPhoneUpdateBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateparolcheak.setOnClickListener {
            viewModel.PhoneUpdate(binding.phonenumber.text.toString().replace("\\s+".toRegex(), ""))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.errorFlow.collect{
                    when(it){
                        ErrorCodes.PASSWORD -> binding.currentCodeerror.error = "Invalid phone"
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.noNetworkFlow.collect{
                    Toast.makeText(requireContext(), "Internet mavjud emas", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openPhoneFlow.collect{
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updatePhone_to_transferVeryfication)
                }
            }
        }
    }
}