package com.ummug.mobilebank.ui.Login

import androidx.lifecycle.ViewModel
import com.ummug.mobilebank.data.repository.LoginRepository.loginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginFragmentViewModel @Inject constructor(private val loginRepository: loginRepository) :ViewModel() {
}