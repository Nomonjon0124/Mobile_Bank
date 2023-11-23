package com.ummug.mobilebank.ui.Verification

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.databinding.FragmentVerificationBinding
import com.ummug.mobilebank.ui.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        binding.resend.setOnClickListener {
            val code1 = binding.inputCode.text.toString()
            Log.d("tag", it.toString())
            if (code1.length==6){
                viewModel.resend(code1,requireContext())
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openHomeFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    val dialog= AlertDialog.Builder(requireContext())
                        .setMessage(it)
                        .setTitle("Verification code")
                        .setPositiveButton("ok") { dialog,i ->
                            dialog.dismiss()
                        }.show()
                    findNavController().navigate(R.id.action_verificationFragment_to_pinFragment)
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
        binding.inputCode.addTextChangedListener {
            if (it?.toString()?.length==6){
                binding.pincodecheak.setBackgroundResource(R.color.button_color)
            }
            else{
                binding.pincodecheak.setBackgroundResource(R.color.button_color_defoult)
            }
        }
        binding.pincodecheak.setOnClickListener {
            val code1 = binding.inputCode.text.toString()
            Log.d("tag", it.toString())
                Toast.makeText(requireContext(), "o'tdi", Toast.LENGTH_SHORT).show()
                viewModel.getToken(code1)
        }
    }
}