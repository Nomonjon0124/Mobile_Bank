package com.ummug.mobilebank.ui.Transfer

import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.TransferMoney
import com.ummug.mobilebank.domain.VarifyTransfer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(

    private val transferMoney: TransferMoney,
    private val varifyTransfer: VarifyTransfer,

) : ViewModel() {

    private val _openTransferFlow = MutableSharedFlow<String>()
    val openTransferFlow: SharedFlow<String> = _openTransferFlow


    private val _errorFlow = MutableStateFlow(0)
    val errorFlow: StateFlow<Int> = _errorFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun transferMoney(from_id:Int,money:String,pan:String){
        viewModelScope.launch {
            val state = transferMoney.invoke(from_id, money, pan)
            handleState(state)
        }
    }




    private suspend fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openTransferFlow.emit(state.data.toString())
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
        }
    }
}