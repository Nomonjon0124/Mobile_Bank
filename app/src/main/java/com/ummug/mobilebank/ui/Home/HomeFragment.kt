package com.ummug.mobilebank.ui.Home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.database.Database
import com.ummug.mobilebank.databinding.FragmnetHomeBinding
import com.ummug.mobilebank.domain.adapters.CardAdapter
import com.ummug.mobilebank.domain.entity.cards.Data
import com.ummug.mobilebank.ui.AddCard.AddCardFragment
import com.ummug.mobilebank.ui.Card.CardFragment
import com.ummug.mobilebank.ui.Transfer.Transfer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragmnet_home) {


    private val viewModel:HomeFragmentViewModel by viewModels()
    private lateinit var dataList:ArrayList<Data>
    private lateinit var list:ArrayList<Int>
    private lateinit var adapter: CardAdapter
    private val binding: FragmnetHomeBinding by viewBinding ()

    private val database by lazy { Database.getDatabase(requireContext()) }
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCards()
        adapter= CardAdapter()
        binding.apply {
            rvCards.adapter=adapter

            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvCards)

            addCard.setOnClickListener{
                parentFragmentManager.
                beginTransaction().
                    addToBackStack("HomeFragment").
                replace(R.id.container,AddCardFragment())
                    .commit()
            }
            Pay.setOnClickListener {
                parentFragmentManager.
                beginTransaction().
                addToBackStack("HomeFragment").
                replace(R.id.container,Transfer())
                    .commit()
            }
        }
        adapter.setOnItemClickListener(object : com.ummug.mobilebank.domain.adapters.OnItemClickListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Karta")
                    .setMessage("Kartaning ma'lumotlari bolimiga otish")
                    .setPositiveButton("OK") { dialog, _ ->
                        parentFragmentManager.beginTransaction()
                            .addToBackStack("HomeFragment")
                            .replace(R.id.container,CardFragment::class.java, bundleOf("id" to database.contactDao().getCards()[position].id.toString(),"index" to position))
                            .commit()
                    }.show()
            }
        })
        dataList= ArrayList()
        list= ArrayList()
        if (database.contactDao().getCards().isNotEmpty()){
            adapter.submitList(database.contactDao().getCards())
            Log.d("sort", database.contactDao().getCards().toString())
        }
        else{
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED){
                    viewModel.openSuccesFlow.collect { data ->
                        data.forEach { i->
                            list.add(i.id)
                        }
                        list.sort()
                        list.forEach { i->
                            data.forEach { j->
                                if (i==j.id){
                                  dataList.add(j)
                                }
                            }
                        }
                        Log.d("sort", dataList.toString())
                        Log.d("sort", list.toString())
                        Log.d("sort", database.contactDao().getCards().toString())
                        adapter.submitList(dataList)
                        if (database.contactDao().getCards().isEmpty()){
                            database.contactDao().insertAll(data)
                        }
                    }
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
}