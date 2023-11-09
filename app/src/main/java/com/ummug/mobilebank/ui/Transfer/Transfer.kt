package com.ummug.mobilebank.ui.Transfer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.databinding.FragmentTransferBinding
import com.ummug.mobilebank.ui.Home.HomeFragment
import com.ummug.mobilebank.ui.Verification.VerificationFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Transfer  : Fragment(R.layout.fragment_transfer) {

    private val viewModel:TransferViewModel by viewModels()
    private val binding:FragmentTransferBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.send.setOnClickListener {
            viewModel.transferMoney(62,binding.transferAmount.text.toString().toInt(),binding.transferCradNumber.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.errorFlow.collect {
                    when (it) {
                        ErrorCodes.MONEY -> binding.transferAmountError.error = "Noto'g'ri"
                        ErrorCodes.PEN_ERROR -> binding.transferCradNumber.error = "Noto'g'ri"
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.noNetworkFlow.collect {
                    Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openTransferFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container, HomeFragment())
                        .commit()
                }
            }
        }
    }
}