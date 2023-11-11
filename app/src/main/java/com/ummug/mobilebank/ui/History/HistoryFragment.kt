package com.ummug.mobilebank.ui.History

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentHistoryBinding
import com.ummug.mobilebank.domain.adapters.IstoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var adapter: IstoryAdapter
    private val viewModel:HistoryViewModel by viewModels()
    private val binding:FragmentHistoryBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}