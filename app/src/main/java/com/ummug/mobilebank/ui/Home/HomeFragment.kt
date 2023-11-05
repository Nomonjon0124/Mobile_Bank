package com.ummug.mobilebank.ui.Home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmnetHomeBinding
import com.ummug.mobilebank.domain.adapters.CardAdapter
import com.ummug.mobilebank.domain.entity.cards.Data
import com.ummug.mobilebank.ui.PinFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragmnet_home) {


    private val viewModel:HomeFragmentViewModel by viewModels()
    private lateinit var dataList:ArrayList<Data>
    private lateinit var adapter: CardAdapter

    private val binding: FragmnetHomeBinding by viewBinding ()
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCards()
        adapter= CardAdapter()
        binding.apply {
            rvCards.adapter=adapter

            addCard.setOnClickListener{
                parentFragmentManager.beginTransaction().replace(R.id.container,PinFragment()).commit()
            }
            Pay.setOnClickListener {
                val dialog= AlertDialog.Builder(requireContext())
                    .setTitle("Kartani ochirish")
                    .setMessage("Kartan ochirasizmi")
                    .setPositiveButton("OK",){dialog,_->
                        dataList.removeAt(dataList.size-1)
                        adapter.notifyItemRemoved(dataList.size-1)
                        adapter.notifyDataSetChanged()
                    }.show()
            }
        }

        dataList= ArrayList()
        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openSuccesFlow.collect { data ->
                    dataList.addAll(data)

                    adapter.submitList(dataList)
                }
            }


            adapter.setOnClickClickListener { inex->
                val dialog=AlertDialog.Builder(requireContext())
                    .setTitle("Kartani ochirish")
                    .setMessage("Kartan ocirasizmi")
                    .setPositiveButton("OK",){dialog,_->
                        dataList.removeAt(inex)
                        adapter.submitList(dataList)
                    }.show()

            }
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openErrorFlow.collect{error->
                    if (error==1){
                        Toast.makeText(requireContext(), "Muammoni bartaraf etish kerak ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openNetworkFlow.collect{
                    Toast.makeText(requireContext(), "Internetizni yangilang", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}