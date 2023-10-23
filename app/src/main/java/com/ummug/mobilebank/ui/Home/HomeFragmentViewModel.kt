package com.ummug.mobilebank.ui.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ummug.mobilebank.domain.entity.CardEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor():ViewModel() {
    private val _contacts = MutableLiveData<List<CardEntity>>()
    val contacts: LiveData<List<CardEntity>> get() = _contacts

}