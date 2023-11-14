package com.ummug.mobilebank.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ummug.mobilebank.R
import com.ummug.mobilebank.ui.History.HistoryFragment
import com.ummug.mobilebank.ui.Home.HomeFragment
import com.ummug.mobilebank.ui.SettingsFragment.SettingsFragment

class BaseFragment : Fragment(R.layout.fragment_base) {

    private lateinit var navicayion: BottomNavigationView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.beginTransaction()
            .replace(R.id.basefrag,HomeFragment())
            .commit()
        navicayion=view.findViewById(R.id.bottomNavigationViewbase)
        navicayion.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.settings -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.basefrag, SettingsFragment())
                        .commit()
                    true
                }
                R.id.accounts -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.basefrag, HomeFragment())
                        .commit()
                    true
                }
                R.id.statistics -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.basefrag, HistoryFragment())
                        .commit()
                    true
                }
                else->false
            }
        }
    }
}