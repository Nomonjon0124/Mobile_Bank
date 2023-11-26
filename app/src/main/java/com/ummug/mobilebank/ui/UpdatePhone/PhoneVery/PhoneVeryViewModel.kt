package com.ummug.mobilebank.ui.UpdatePhone.PhoneVery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.UpdatePhoneVeryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneVeryViewModel @Inject constructor(
    private val updatePhoneVeryUseCase: UpdatePhoneVeryUseCase
) :ViewModel(){
    private val _openPhoneVeryFlow = MutableSharedFlow<Unit>()
    val openPhoneVeryFlow: SharedFlow<Unit> = _openPhoneVeryFlow


    private val _errorFlow = MutableStateFlow(0)
    val errorFlow: StateFlow<Int> = _errorFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow


    fun PhoneVey(code:String){
        viewModelScope.launch {
            val state = updatePhoneVeryUseCase.invoke(code)
            handleState(state)
        }
    }

    private suspend fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openPhoneVeryFlow.emit(Unit)
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
        }
    }
}