package com.ummug.mobilebank.ui.UpdateFullName

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.UpdateFuulNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateFullnameViewModel @Inject constructor(
    private val updateFullname: UpdateFuulNameUseCase
) :ViewModel() {

    private val _openUpdatenameFlow = MutableSharedFlow<Unit>()
     val openUpdatenameFlow : SharedFlow<Unit> = _openUpdatenameFlow

        private val _errorFlow= MutableStateFlow(0)
    val errorFlow : StateFlow<Int> =_errorFlow

    private val _nonetwork = MutableSharedFlow<Unit>()
    val nonetwork : SharedFlow<Unit> =_nonetwork

    fun updateName(firstName:String,lastName:String){
        viewModelScope.launch {
            val state = updateFullname.invoke(firstName, lastName)
            handleState(state)
        }
    }

    private suspend fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openUpdatenameFlow.emit(Unit)
            is State.Error -> _errorFlow.emit(state.code)
            State.NoNetwork -> _nonetwork.emit(Unit)
        }
    }
}