package com.ummug.mobilebank.ui.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.databinding.FragmentRegisterBinding
import com.ummug.mobilebank.ui.Verification.VerificationFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel:RegisterFragmentViewModel by viewModels()

    private var _binding: FragmentRegisterBinding?=null ;
    private val binding get() = _binding!!

    private lateinit var settings: Settings


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.famale.setOnClickListener {
                binding.male.isChecked=false
                if(binding.famale.isChecked){
                    binding.register.setBackgroundResource(R.color.button_color)
                }
                else if (!binding.famale.isChecked){
                    binding.register.setBackgroundResource(R.color.button_color_defoult)
                }
            }
           binding.male.setOnClickListener {
                binding.famale.isChecked=false
                if(binding.male.isChecked){
                    binding.register.setBackgroundResource(R.color.button_color)
                }
                else if (!binding.male.isChecked){
                    binding.register.setBackgroundResource(R.color.button_color_defoult)
                }
            }

            viewModel.openVerifyLiveData.observe(viewLifecycleOwner, openVerifyLiveDataObserver)
            viewModel.errorLiveData.observe(viewLifecycleOwner, errorLiveDataObserver)
            viewModel.noNetworkLiveData.observe(viewLifecycleOwner, noNetworkLiveDataObserver)
            binding.register.setOnClickListener {
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

    private val openVerifyLiveDataObserver: Observer<Unit> = Observer {
        Toast.makeText(requireContext(), "Verifyni och", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container,VerificationFragment())
                .commit()

    }

    private val errorLiveDataObserver: Observer<Int> = Observer { error ->

        when (error) {
            ErrorCodes.FIRST_NAME_ERROR -> binding.firstNameError.error = "Noto'g'ri"
            ErrorCodes.LAST_NAME_ERROR -> binding.lastNameError.error = "Noto'g'ri"
            ErrorCodes.PHONE_NUMBER -> binding.phoneNameError.error = "Noto'g'ri"
            ErrorCodes.PASSWORD -> binding.passwordNameError.error = "Noto'g'ri"
        }
    }

    private val noNetworkLiveDataObserver: Observer<Unit> = Observer {
        Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
    }
}