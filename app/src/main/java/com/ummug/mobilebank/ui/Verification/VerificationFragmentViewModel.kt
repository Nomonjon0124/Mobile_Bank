package com.ummug.mobilebank.ui.Verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.VerificationRepository.verificationRepository
import com.ummug.mobilebank.domain.SignVerifiUseToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationFragmentViewModel @Inject constructor(
    private val signVerifiUseToken: SignVerifiUseToken
) : ViewModel(){

    private val _openVerifyLiveData = MutableLiveData<String>()
    val openVerifyLiveData: LiveData<String> get() = _openVerifyLiveData
    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> get() = _errorLiveData
    private val _noNetworkLiveData = MutableLiveData<Unit>()
    val noNetworkLiveData: LiveData<Unit> get() = _noNetworkLiveData

    fun getToken( code: String?, token: String?) {
        viewModelScope.launch {
            val state = signVerifiUseToken(code,token)
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