package com.ummug.mobilebank.ui.Register

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.databinding.FragmentRegisterBinding
import com.ummug.mobilebank.ui.Verification.VerificationFragment
import dagger.hilt.android.AndroidEntryPoint

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
    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openVerifyLiveData.observe(viewLifecycleOwner, openVerifyLiveDataObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.noNetworkLiveData.observe(viewLifecycleOwner, noNetworkLiveDataObserver)

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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private val openVerifyLiveDataObserver: Observer<String> = Observer { it->

        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.container,VerificationFragment::class.java, bundleOf("token" to it))
            .commit()
    }

    private val errorLiveDataObserver: Observer<Int> = Observer { error ->
        when (error) {
            ErrorCodes.FIRST_NAME_ERROR -> binding.firstNameError.error = "Noto'g'ri"
            ErrorCodes.LAST_NAME_ERROR -> binding.lastNameError.error = "Noto'g'ri"
            ErrorCodes.PHONE_NUMBER -> binding.passwordNameError.error = "Noto'g'ri"
            ErrorCodes.PASSWORD -> binding.passwordNameError.error = "Noto'g'ri"
        }
    }

    private val noNetworkLiveDataObserver: Observer<Unit> = Observer {
        Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
    }




}