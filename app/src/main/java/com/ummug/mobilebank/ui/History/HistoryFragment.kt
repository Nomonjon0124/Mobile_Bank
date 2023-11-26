package com.ummug.mobilebank.ui.History

import android.os.Bundle
import android.util.Log
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
import com.ummug.mobilebank.databinding.FragmentHistoryBinding
import com.ummug.mobilebank.domain.adapters.ForHistoryCardAdapter
import com.ummug.mobilebank.domain.adapters.IstoryAdapter
import com.ummug.mobilebank.domain.entity.cards.Data
import com.ummug.mobilebank.ui.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var adapter: IstoryAdapter
    private val viewModel:HistoryViewModel by viewModels()
    private val binding:FragmentHistoryBinding by viewBinding()

    private lateinit var dataList:ArrayList<com.ummug.mobilebank.domain.entity.History.Data>

    private lateinit var carddateList:ArrayList<com.ummug.mobilebank.domain.entity.cardistory.Data>
    private lateinit var cardadapter:ForHistoryCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listHistory()
        binding.payments.setOnClickListener {
            viewModel.listHistory()
        }
        binding.cards.setOnClickListener {
            viewModel.cardhistory(62)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openSuccesFlow.collect { data ->
                   dataList= ArrayList(data.data)
                    adapter= IstoryAdapter(dataList) { _, _ -> }
                    binding.rvHistory.adapter=adapter
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openCardHistoryFlow.collect { data ->
                    carddateList= ArrayList(data.data)
                    cardadapter= ForHistoryCardAdapter(carddateList) { _, _ -> }
                    binding.rvHistory.adapter=adapter

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openErrorFlow.collect{
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openNetworkFlow.collect{
                    Toast.makeText(requireContext(), "internet yoq", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}