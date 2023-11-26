package com.ummug.mobilebank.ui.UpdatePassword

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
import com.ummug.mobilebank.databinding.FragmentChanePasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UpdatePassword :Fragment(R.layout.fragment_chane_password) {

    private val viewModel:UpdatePasswordViewModel by viewModels()

    private val binding:FragmentChanePasswordBinding  by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateparolcheak.setOnClickListener {
            val current=binding.currentCode.text.toString()
            val new=binding.newPassword.text.toString()
            val confirm=binding.confirmPassword.text.toString()
            viewModel.updateParol(current,new,confirm)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.errorFlow.collect{
                    when(it){
                        ErrorCodes.PASSWORD -> binding.currentCodeerror.error = "Password uzunligi kamida 4 boladi"
                        ErrorCodes.new_parol -> binding.newPassworderror.error = "Password uzunligi kamida 4 boladi"
                        ErrorCodes.new_confirm_parol -> binding.confirmPassworderror.error = "Password uzunligi kamida 4 boladi"
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
                viewModel.openTransferVeryFlow.collect{
                    findNavController().navigate(R.id.action_updatePassword_to_succesFull)
                }
            }
        }
    }
}