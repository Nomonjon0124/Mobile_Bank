package com.ummug.mobilebank.ui.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.domain.GetCardsUseCase
import com.ummug.mobilebank.domain.entity.Bearer_token
import com.ummug.mobilebank.domain.entity.CardEntity
import com.ummug.mobilebank.domain.entity.ListCards
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
):ViewModel() {


    private val _openVerifyFlow = MutableSharedFlow<List<ListCards>>()
    val openVerifyFlow: SharedFlow<List<ListCards>> = _openVerifyFlow

    fun getCards(bearerToken: String){
        viewModelScope.launch {
            val invoke = getCardsUseCase.invoke(bearerToken)
            _openVerifyFlow.emit(invoke)
        }
    }


}