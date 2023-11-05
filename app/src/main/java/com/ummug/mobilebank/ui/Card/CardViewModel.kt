package com.ummug.mobilebank.ui.Card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.CardNameUpdate
import com.ummug.mobilebank.domain.DeleteUseCase
import com.ummug.mobilebank.domain.GetCardsUseCase
import com.ummug.mobilebank.domain.UpdateUseCase
import com.ummug.mobilebank.domain.entity.cards.CardResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CardViewModel @Inject constructor(
    private val deleteUseCase: DeleteUseCase,
    private val updateUseCase: UpdateUseCase
): ViewModel(){

    private val _openSuccsesDeleteFlow = MutableSharedFlow<Unit>()
    val openSuccesDeleteFlow: SharedFlow<Unit> =_openSuccsesDeleteFlow

    private val _openSuccsesUpdateFlow = MutableSharedFlow<CardResponse>()
    val openSuccesUpdateFlow: SharedFlow<CardResponse> =_openSuccsesUpdateFlow

    private val _openErrorFlow = MutableSharedFlow<Int>()
    val openErrorFlow: SharedFlow<Int> =_openErrorFlow

    private val _openNetworkFlow = MutableSharedFlow<Unit>()
    val openNetworkFlow: SharedFlow<Unit> =_openNetworkFlow
    fun delete(id:String) {
        viewModelScope.launch {
            val state = deleteUseCase.invoke(id)
            handleState(state)
        }
    }

    fun update(name:String,theme:Int,id:String){
        viewModelScope.launch() {
            val state = updateUseCase.invoke(name, theme, id)
            handleState1(state)
        }
    }

    private suspend fun handleState1(state: State) {
        when(state){
            is State.Error -> _openErrorFlow.emit(state.code)
            State.NoNetwork -> _openNetworkFlow.emit(Unit)
            is State.Success<*> -> _openSuccsesUpdateFlow.emit((state.data as CardResponse))
        }
    }

    private suspend fun handleState(state: State) {
        when(state){
            is State.Error -> _openErrorFlow.emit(state.code)
            State.NoNetwork -> _openNetworkFlow.emit(Unit)
            is State.Success<*> -> _openSuccsesDeleteFlow.emit(Unit)
        }
    }

}