package com.ummug.mobilebank.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.SignInUseCase
import com.ummug.mobilebank.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) :ViewModel() {

    private val _openVerifyLiveData = MutableLiveData<String>()
    val openVerifyLiveData: LiveData<String> get() = _openVerifyLiveData
    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> get() = _errorLiveData
    private val _noNetworkLiveData = MutableLiveData<Unit>()


    val noNetworkLiveData: LiveData<Unit> get() = _noNetworkLiveData


    fun signIn(  phone: String?,password: String?) {
        viewModelScope.launch {
            val state = signInUseCase(phone,password)
            handleState(state)
        }
    }


    private fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openVerifyLiveData.postValue(state.data.toString())
            is State.Error -> _errorLiveData.postValue(state.code)
            State.NoNetwork -> _noNetworkLiveData.postValue(Unit)
        }

    }
}