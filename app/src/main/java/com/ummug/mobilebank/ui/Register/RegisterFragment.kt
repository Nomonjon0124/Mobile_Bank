package com.ummug.mobilebank.ui.Register

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.databinding.FragmentRegisterBinding
import com.ummug.mobilebank.ui.Verification.VerificationFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: RegisterFragmentViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null;
    private val binding get() = _binding!!


    private lateinit var settings: Settings


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?


    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }
        binding.dateEt.setOnClickListener {
            DatePickerDialog(requireContext(),datePicker,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.famale.setOnClickListener {
            binding.male.isChecked = false
            if (binding.famale.isChecked) {
                binding.register.setBackgroundResource(R.color.button_color)
            } else if (!binding.famale.isChecked) {
                binding.register.setBackgroundResource(R.color.button_color_defoult)
            }
        }
        binding.male.setOnClickListener {
            binding.famale.isChecked = false
            if (binding.male.isChecked) {
                binding.register.setBackgroundResource(R.color.button_color)
            } else if (!binding.male.isChecked) {
                binding.register.setBackgroundResource(R.color.button_color_defoult)
            }
        }

        viewModel.openVerifyLiveData.observe(viewLifecycleOwner, openVerifyLiveDataObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.noNetworkLiveData.observe(viewLifecycleOwner, noNetworkLiveDataObserver)
        binding.register.setOnClickListener {
            if (binding.male.isChecked || binding.famale.isChecked) {
                val firstName = binding.firstName.text?.toString()
                val lastName = binding.lastName.text?.toString()
                val phone = binding.phoneNumber.text?.toString()
                val password = binding.password.text?.toString()
                viewModel.signUp(firstName, lastName, password, phone)

            } else {
                Toast.makeText(requireContext(), "choose your gender", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.dateEt.setText(sdf.format(myCalendar.time))
    }

    private val openVerifyLiveDataObserver: Observer<Unit> = Observer {
        Toast.makeText(requireContext(), "Verifyni och", Toast.LENGTH_SHORT).show()
        parentFragmentManager.beginTransaction().replace(R.id.container, VerificationFragment())
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