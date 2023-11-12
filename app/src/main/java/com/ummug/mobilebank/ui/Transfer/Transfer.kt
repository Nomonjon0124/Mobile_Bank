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
import com.ummug.mobilebank.database.Database
import com.ummug.mobilebank.databinding.FragmentTransferBinding
import com.ummug.mobilebank.domain.adapters.CardAdapter
import com.ummug.mobilebank.ui.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Transfer  : Fragment(R.layout.fragment_transfer) {

    private val viewModel:TransferViewModel by viewModels()
    private val binding:FragmentTransferBinding by viewBinding()

    private lateinit var adapter: CardAdapter
    private val database by lazy { Database.getDatabase(requireContext()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(), database.contactDao().getCards().get(0).pan, Toast.LENGTH_SHORT).show()
        Toast.makeText(requireContext(), "salom", Toast.LENGTH_SHORT).show()

        binding.send.setOnClickListener {
            viewModel.transferMoney(62,binding.transferAmount.text.toString(),binding.transferCradNumber.text.toString())
        }
//        adapter= CardAdapter()
//        adapter.submitList(database.contactDao().getCards())
//        Toast.makeText(requireContext(), database.contactDao().getCards().get(0).pan, Toast.LENGTH_SHORT).show()
//        Toast.makeText(requireContext(), "salom", Toast.LENGTH_SHORT).show()
//
//        binding.transferRV.adapter=adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.errorFlow.collect {
                    when (it) {
                        ErrorCodes.MONEY -> binding.transferAmountError.error = "Eng kam summa 5000 so'm"
                        ErrorCodes.PEN_ERROR -> binding.transferCradNumber.error = "Karta raqami notog'ri kiritildi"
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