package com.ummug.mobilebank.ui.History

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.HistoryUseCase
import com.ummug.mobilebank.domain.entity.History.HistoryRsponse
import com.ummug.mobilebank.domain.entity.cardistory.cardHistory
import com.ummug.mobilebank.domain.entity.cards.Data
import com.ummug.mobilebank.domain.getHistoryForCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyUseCase: HistoryUseCase,
    private val getHistoryForCard: getHistoryForCard
):ViewModel(){

    private val _openSuccsesFlow = MutableSharedFlow<HistoryRsponse>()
    val openSuccesFlow: SharedFlow<HistoryRsponse> =_openSuccsesFlow

    private val _openCardHistoryFlow = MutableSharedFlow<cardHistory>()
    val openCardHistoryFlow: SharedFlow<cardHistory> =_openCardHistoryFlow


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

    fun cardhistory(cardId:Int){
        viewModelScope.launch {
            val state = getHistoryForCard.invoke(cardId)
            handleState1(state)
        }
    }
    private suspend fun handleState(state: State) {
        when(state){
            is State.Error -> _openErrorFlow.emit(state.code)
            State.NoNetwork -> _openNetworkFlow.emit(Unit)
            is State.Success<*>->_openSuccsesFlow.emit(state.data as HistoryRsponse)
        }
    }

    private suspend fun handleState1(state: State) {
        when(state){
            is State.Error -> _openErrorFlow.emit(state.code)
            State.NoNetwork -> _openNetworkFlow.emit(Unit)
            is State.Success<*>->_openCardHistoryFlow.emit(state.data as cardHistory)
        }
    }
}