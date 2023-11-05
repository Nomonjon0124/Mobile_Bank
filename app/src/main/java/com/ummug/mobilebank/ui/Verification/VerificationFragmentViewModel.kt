package com.ummug.mobilebank.ui.Verification

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.ResendUseCase
import com.ummug.mobilebank.domain.SignVerifiUseToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationFragmentViewModel @Inject constructor(
    private val signVerifiUseToken: SignVerifiUseToken,
    private val resendUseCase: ResendUseCase,
    private val settings: Settings
) : ViewModel(){

    private val _openHomeFlow = MutableSharedFlow<String>()
    val openHomeFlow: SharedFlow<String> = _openHomeFlow


    private val _errorFlow = MutableStateFlow(0)
    val errorFlow: StateFlow<Int> = _errorFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow

    fun getToken( code: String) {
        viewModelScope.launch {
            val state = signVerifiUseToken(settings.temporaryToken,code)
            handleState(state)
        }
    }
    fun resend(code:String,context:Context){
        viewModelScope.launch {
           val state= resendUseCase(settings.temporaryToken.toString())
            if (code == settings.code){
                handleState(state)
            }
            else{
                Toast.makeText(context, "Parol xato", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private suspend fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> {
                _openHomeFlow.emit(state.data.toString())}
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
        }

    }
}