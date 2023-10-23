package com.ummug.mobilebank.ui.Home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ummug.mobilebank.R
import com.ummug.mobilebank.domain.adapters.CardsAdapter
import com.ummug.mobilebank.domain.entity.CardEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragmnet_home) {
    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var adapter: CardsAdapter
    private lateinit var data: ArrayList<CardEntity>
    private lateinit var rvCArds: RecyclerView

    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCArds = view.findViewById(R.id.rv_cards)
        loadList()

        viewModel.contacts.observe(viewLifecycleOwner) {
            adapter.setList(it)
            data.clear()
            data.addAll(it)
        }
        adapter = CardsAdapter(data)
        rvCArds.adapter = adapter
    }

    private fun loadList() {
        data = ArrayList()
        for (i in 1..10) {
            data.add(CardEntity(expire_month = i,expire_year =28, name = "Humo", pan = "4916990200137781"))
        }
    }
}