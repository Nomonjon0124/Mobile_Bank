package com.ummug.mobilebank.ui.Login


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.databinding.FragmentLoginBinding
import com.ummug.mobilebank.ui.PinFragment
import com.ummug.mobilebank.ui.Register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }

    private val viewModel: LoginFragmentViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openVerifyLiveData.observe(viewLifecycleOwner, openVerifyLiveDataObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.noNetworkLiveData.observe(viewLifecycleOwner, noNetworkLiveDataObserver)

            binding.bottomSignIn.setOnClickListener {

                var phone = binding.phoneSignIn.text?.toString()
                phone = phone?.replace("\\s+".toRegex(), "")


                val password = binding.passwordSignIn.text?.toString()
                viewModel.signIn(phone, password)
            }
        binding.registratsiya.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
        private val openVerifyLiveDataObserver: Observer<String> = Observer { it ->
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_pinFragment)
        }
        private val errorLiveDataObserver: Observer<Int> = Observer { error ->
            when (error) {
                ErrorCodes.PHONE_NUMBER -> binding.phoneSignInError.error = "Noto'g'ri"
                ErrorCodes.PASSWORD -> binding.passwordSignInError.error = "Noto'g'ri"
            }
        }

        private val noNetworkLiveDataObserver: Observer<Unit> = Observer {
            Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
        }
    }
