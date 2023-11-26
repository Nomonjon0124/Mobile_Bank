package com.ummug.mobilebank.ui.Transfer.TransferVery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.TransferMoneyVery
import com.ummug.mobilebank.domain.VarifyTransfer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferVeryViewModel @Inject constructor(
    private val varifyTransfer: TransferMoneyVery
) : ViewModel() {

    private val _openTransferVeryFlow = MutableSharedFlow<Unit>()
    val openTransferVeryFlow: SharedFlow<Unit> = _openTransferVeryFlow


    private val _errorFlow = MutableStateFlow(0)
    val errorFlow: StateFlow<Int> = _errorFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun transferMoneyVery(code:String){
        viewModelScope.launch {
            val state = varifyTransfer.invoke(code)
            handleState(state)
        }
    }

    private suspend fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openTransferVeryFlow.emit(Unit)
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
        }
    }
}