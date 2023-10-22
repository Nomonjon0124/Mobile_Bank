package com.ummug.mobilebank.ui.Home

import androidx.lifecycle.ViewModel
import com.ummug.mobilebank.data.repository.HomeRepository.homeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor( private val homeRepository: homeRepository):ViewModel() {
}