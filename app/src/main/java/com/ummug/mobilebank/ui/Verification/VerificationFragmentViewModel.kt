package com.ummug.mobilebank.ui.Verification

import androidx.lifecycle.ViewModel
import com.ummug.mobilebank.data.VerificationRepository.verificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationFragmentViewModel @Inject constructor(private val verificationRepository: verificationRepository) : ViewModel(){
}