package com.ummug.mobilebank.ui.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.DeleteUseCase
import com.ummug.mobilebank.domain.GetCardsUseCase
import com.ummug.mobilebank.domain.entity.cards.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getCardUseCase: GetCardsUseCase,
    private val deleteUseCase: DeleteUseCase
):ViewModel() {


    private val _openSuccsesFlow = MutableSharedFlow<List<Data>>()
    val openSuccesFlow:SharedFlow<List<Data>> =_openSuccsesFlow


    private val _openErrorFlow = MutableSharedFlow<Int>()
    val openErrorFlow:SharedFlow<Int> =_openErrorFlow

    private val _openNetworkFlow = MutableSharedFlow<Unit>()
    val openNetworkFlow:SharedFlow<Unit> =_openNetworkFlow

    fun getCards(){
        viewModelScope.launch {
            val state=getCardUseCase.invoke()
            handleState(state)
        }
    }




    private suspend fun handleState(state: State) {
        when(state){
            is State.Error -> _openErrorFlow.emit(state.code)
            State.NoNetwork -> _openNetworkFlow.emit(Unit)
            is State.Success<*>->_openSuccsesFlow.emit(state.data as List<Data>)
        }
    }

}