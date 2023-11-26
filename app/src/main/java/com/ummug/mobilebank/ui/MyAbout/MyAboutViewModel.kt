package com.ummug.mobilebank.ui.MyAbout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.MyAboutUseCase
import com.ummug.mobilebank.domain.entity.profile.My_about
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAboutViewModel @Inject constructor(
    private val myAboutUseCase: MyAboutUseCase
):ViewModel() {

    private val _openAboutFlow = MutableSharedFlow<My_about>()
    val openAboutFlow: SharedFlow<My_about> = _openAboutFlow


    private val _errorFlow = MutableStateFlow(0)
    val errorFlow: StateFlow<Int> = _errorFlow

    private val _noNetworkFlow = MutableSharedFlow<Unit>()
    val noNetworkFlow: SharedFlow<Unit> = _noNetworkFlow
    fun MyAbout(){
        viewModelScope.launch {
            val state = myAboutUseCase.invoke()
            handleState(state)
        }
    }


    private suspend fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openAboutFlow.emit(state.data as My_about)
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _noNetworkFlow.emit(Unit)
        }
    }
}