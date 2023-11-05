package com.ummug.mobilebank.ui.Register

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.databinding.FragmentRegisterBinding
import com.ummug.mobilebank.ui.Verification.VerificationFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }

    private val viewModel:RegisterFragmentViewModel by viewModels()

    private var _binding: FragmentRegisterBinding?=null ;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission", "UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openVerifyFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    val dialog= AlertDialog.Builder(requireContext())
                        .setMessage(it)
                        .setTitle("Verification code")
                        .setPositiveButton("ok") { dialog,i ->
                            dialog.dismiss()
                        }.show()

                    parentFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container,VerificationFragment())
                        .commit()
                }
            }
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.errorFlow.collect { error ->
                    when (error) {
                        ErrorCodes.FIRST_NAME_ERROR -> binding.firstNameError.error = "Noto'g'ri"
                        ErrorCodes.LAST_NAME_ERROR -> binding.lastNameError.error = "Noto'g'ri"
                        ErrorCodes.PHONE_NUMBER -> binding.phoneNameError.error = "Noto'g'ri"
                        ErrorCodes.PASSWORD -> binding.passwordNameError.error = "Noto'g'ri"
                    }
                }
            }
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.noNetworkFlow.collect {
                    Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.apply {
            famale.setOnClickListener {
                male.isChecked = false
                if (famale.isChecked) {
                    register.setBackgroundResource(R.color.button_color)
                } else if (!famale.isChecked) {
                    register.setBackgroundResource(R.color.button_color_defoult)
                }
            }
            male.setOnClickListener {
                famale.isChecked = false
                if (male.isChecked) {
                    register.setBackgroundResource(R.color.button_color)
                } else if (!male.isChecked) {
                    register.setBackgroundResource(R.color.button_color_defoult)
                }
            }
        }
           binding. register.setOnClickListener {
                if (binding.male.isChecked || binding.famale.isChecked){
                    val firstName = binding.firstName.text?.toString()
                    val lastName = binding.lastName.text?.toString()
                    val phone = binding.phoneNumber.text?.toString()
                    val password = binding.password.text?.toString()
                    viewModel.signUp(firstName, lastName, password, phone)
                }
                else{
                    Toast.makeText(requireContext(), "choose your gender", Toast.LENGTH_SHORT).show()
                }
            }
        }
}