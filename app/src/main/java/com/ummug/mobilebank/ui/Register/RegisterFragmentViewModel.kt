package com.ummug.mobilebank.ui.Register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.SignUpUseCase
import com.ummug.mobilebank.domain.entity.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {


    private val _openVerifyFlow = MutableSharedFlow<String>()
    val openVerifyFlow: SharedFlow<String> = _openVerifyFlow

    private val _errorFlow = MutableStateFlow(0)
    val errorFlow: StateFlow<Int> = _errorFlow
    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow





    fun signUp(firstName: String?, lastName: String?, password: String?, phone: String?) {
        viewModelScope.launch {
            val state = signUpUseCase(firstName, lastName, password, phone)
            handleState(state)
        }
    }


    private suspend fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openVerifyFlow.emit(state.data.toString())
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
        }

    }
}