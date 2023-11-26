package com.ummug.mobilebank.ui.UpdateFullName

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.databinding.FragmentUpdatefullnameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateFullname :Fragment(R.layout.fragment_updatefullname) {
    private val viewModel:UpdateFullnameViewModel by viewModels ()

    private val binding:FragmentUpdatefullnameBinding by viewBinding ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateparolcheak.setOnClickListener {
            val name=binding.currentCode.text.toString()
            val name1=binding.newPassword.text.toString()
            viewModel.updateName(name,name1)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.errorFlow.collect{
                    when(it){
                        ErrorCodes.FIRST_NAME_ERROR -> binding.currentCodeerror.error = "Familya uzunligi kamida 4 boladi"
                        ErrorCodes.LAST_NAME_ERROR -> binding.newPassworderror.error = "Ism uzunligi kamida 4 boladi"
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.nonetwork.collect{
                    Toast.makeText(requireContext(), "Intenet mavjud emas", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openUpdatenameFlow.collect{
                    findNavController().navigate(R.id.action_updateFullname_to_succesFull)
                }
            }
        }
    }
}