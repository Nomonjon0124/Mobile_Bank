package com.ummug.mobilebank.ui.UpdatePhone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.UpdatePhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePhoneViewModel @Inject constructor(
    private val updatePhoneUseCase: UpdatePhoneUseCase
) :ViewModel(){

    private val _openPhoneFlow = MutableSharedFlow<String>()
    val openPhoneFlow: SharedFlow<String> = _openPhoneFlow


    private val _errorFlow = MutableStateFlow(0)
    val errorFlow: StateFlow<Int> = _errorFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow


    fun PhoneUpdate(phone:String){
        viewModelScope.launch {
            val state = updatePhoneUseCase.invoke(phone)
            handleState(state)
        }
    }
    private suspend fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openPhoneFlow.emit(state.data.toString())
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
        }
    }
}