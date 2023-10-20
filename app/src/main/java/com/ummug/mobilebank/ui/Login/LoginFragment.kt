package com.ummug.mobilebank.ui.Login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ummug.mobilebank.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :Fragment(R.layout.fragment_login) {

    private val loginFragmentViewModel:LoginFragmentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}