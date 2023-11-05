package com.ummug.mobilebank.ui.AddCard

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.AddCardUseCase
import com.ummug.mobilebank.domain.entity.AddCardEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardFragmentViewModel @Inject constructor(private val addCardUseCase: AddCardUseCase): ViewModel() {

    private val _openSuccsesScreenFlow= MutableSharedFlow<String>()
    val openSuccsesScreenFlow: SharedFlow<String> = _openSuccsesScreenFlow

    private val _openErrorFlow= MutableSharedFlow<Int>()
    val openErrorFlow:SharedFlow<Int> = _openErrorFlow

    private val _openNetworkFlow= MutableSharedFlow<Unit>()
    val openNetworkFlow:SharedFlow<Unit> = _openNetworkFlow

    fun addCard(year:Int,month:Int,cardname:String,pan:String){

        viewModelScope.launch {
            val state = addCardUseCase.invoke(pan,cardname,year,month)
            handleState(state)

        }
    }

    private suspend fun handleState(state: State) {
        when(state){
            is State.Error -> _openErrorFlow.emit(state.code)
            State.NoNetwork -> _openNetworkFlow.emit(Unit)
            is State.Success<*> -> _openSuccsesScreenFlow.emit(state.data.toString())
        }
    }
}