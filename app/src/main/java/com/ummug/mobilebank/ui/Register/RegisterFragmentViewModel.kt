package com.ummug.mobilebank.ui.Register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.RegisterRepository.registerRepository
import com.ummug.mobilebank.domain.SignUpUseCase
import com.ummug.mobilebank.domain.entity.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    private val _openVerifyLiveData = MutableLiveData<Unit>()
    val openVerifyLiveData: LiveData<Unit> get() = _openVerifyLiveData
    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> get() = _errorLiveData
    private val _noNetworkLiveData = MutableLiveData<Unit>()
    val noNetworkLiveData: LiveData<Unit> get() = _noNetworkLiveData


    fun signUp(firstName: String?, lastName: String?, password: String?, phone: String?) {
        viewModelScope.launch {
            val state = signUpUseCase(firstName, lastName, password, phone)
            handleState(state)
        }
    }
    private fun handleState(state: State) {
        when (state) {
            is State.Success<*> -> _openVerifyLiveData.postValue(Unit)
            is State.Error -> _errorLiveData.postValue(state.code)
            State.NoNetwork -> _noNetworkLiveData.postValue(Unit)
        }

    }
}