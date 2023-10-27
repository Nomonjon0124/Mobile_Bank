package com.ummug.mobilebank.ui.Verification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.databinding.FragmentRegisterBinding
import com.ummug.mobilebank.databinding.FragmentVerificationBinding
import com.ummug.mobilebank.ui.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment :Fragment(R.layout.fragment_verification) {

    private val viewModel:VerificationFragmentViewModel by viewModels()
    private var _binding: FragmentVerificationBinding?=null ;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openVerifyLiveData.observe(viewLifecycleOwner, openVerifyLiveDataObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.noNetworkLiveData.observe(viewLifecycleOwner, noNetworkLiveDataObserver)

        val token=arguments?.getString("token")
        Toast.makeText(requireContext(), token, Toast.LENGTH_SHORT).show()

        val code=token.toString().substring(0,6).toString()
        val tok=token.toString().substring(6,token.toString().length).toString()

        Toast.makeText(requireContext(), code, Toast.LENGTH_SHORT).show()
        Toast.makeText(requireContext(), tok, Toast.LENGTH_SHORT).show()
        Log.d("tag", code)
        Log.d("tag", tok)
        Log.d("tag", token.toString())

        val text = binding.inputCode.text.toString()
        binding.inputCode.addTextChangedListener {
            if (it?.toString()?.length==6){
                binding.pincodecheak.setBackgroundResource(R.color.button_color)
            }
            else{
                binding.pincodecheak.setBackgroundResource(R.color.button_color_defoult)
            }
        }
        binding. pincodecheak.setOnClickListener {
            val code1 = binding.inputCode.text?.toString()
            viewModel.getToken(code1,tok)
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container,HomeFragment())
                .commit()
        }
    }

    private val openVerifyLiveDataObserver: Observer<String> = Observer {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.container,HomeFragment())
            .commit()

    }

    private val errorLiveDataObserver: Observer<Int> = Observer { error ->
        when (error) {
            ErrorCodes.CODE_ERROR -> binding.pincodeError.error = "Parol xato"
        }
    }

    private val noNetworkLiveDataObserver: Observer<Unit> = Observer {
        Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
    }
}