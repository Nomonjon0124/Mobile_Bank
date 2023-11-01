package com.ummug.mobilebank.ui.Verification

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.databinding.FragmentVerificationBinding
import com.ummug.mobilebank.ui.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

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
    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openHomeFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container,HomeFragment())
                        .commit()
                }
            }
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.errorFlow.collect { error ->
                    when (error) {
                        ErrorCodes.CODE_ERROR -> binding.pincodeError.error = "Parol xato"
                    }
                }
            }
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.noNetworkFlow.collect {
                    Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val token=arguments?.getString("token")

        val code=token.toString().substring(0,6).toString()
        val tok=token.toString().substring(6,token.toString().length).toString()

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
            val code1 = binding.inputCode.text.toString()
            Log.d("tag", it.toString())
                Toast.makeText(requireContext(), "o'tdi", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container,HomeFragment())

                viewModel.getToken(tok, code1)
        }
    }
}