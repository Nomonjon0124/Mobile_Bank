package com.ummug.mobilebank.ui.History

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.HistoryUseCase
import com.ummug.mobilebank.domain.entity.History.HistoryRsponse
import com.ummug.mobilebank.domain.entity.cards.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyUseCase: HistoryUseCase
):ViewModel(){

    private val _openSuccsesFlow = MutableSharedFlow<HistoryRsponse>()
    val openSuccesFlow: SharedFlow<HistoryRsponse> =_openSuccsesFlow


    private val _openErrorFlow = MutableSharedFlow<Int>()
    val openErrorFlow: SharedFlow<Int> =_openErrorFlow

    private val _openNetworkFlow = MutableSharedFlow<Unit>()
    val openNetworkFlow: SharedFlow<Unit> =_openNetworkFlow


    fun listHistory(){
        viewModelScope.launch {
            val state = historyUseCase.invoke()
            handleState(state)
        }
    }
    private suspend fun handleState(state: State) {
        when(state){
            is State.Error -> _openErrorFlow.emit(state.code)
            State.NoNetwork -> _openNetworkFlow.emit(Unit)
            is State.Success<*>->_openSuccsesFlow.emit(state.data as HistoryRsponse)
        }
    }
}