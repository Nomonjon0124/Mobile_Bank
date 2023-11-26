package com.ummug.mobilebank.ui.Card

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.database.Database
import com.ummug.mobilebank.databinding.FragmentCardBinding
import com.ummug.mobilebank.ui.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardFragment : Fragment(R.layout.fragment_card) {

    private val viewModel:CardViewModel by viewModels()
    private val binding:FragmentCardBinding by viewBinding()
    private val database by lazy { Database.getDatabase(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id=arguments?.getString("id")
        val index=arguments?.getInt("index")

        binding.name.text= database.contactDao().getCards()[index!!].name
        binding.cardNumber.text= database.contactDao().getCards()[index].pan
        binding.cardBalance.text= database.contactDao().getCards()[index].amount


        binding.Ochirish.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Karta o'chirish")
                .setMessage("Kartani ochirmoqchimisiz")
                .setPositiveButton("OK") { dialog, _ ->
                    viewModel.delete(id.toString())

                    dialog.dismiss()
                    database.contactDao().deletecard(database.contactDao().getCards()[index])
                }.show()
        }
        binding.Saqlash.setOnClickListener {
            viewModel.update(binding.CardName.text.toString(),3,id.toString())
            database.contactDao().nukeTable()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openErrorFlow.collect { code ->
                    when (code) {
                        ErrorCodes.CARD_NAME -> binding.CardNameError.error = "Card name error"
                    }

                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openNetworkFlow.collect{
                    Toast.makeText(requireContext(), "Internet Mavjud emas", Toast.LENGTH_SHORT).show()
                }
            }}
        viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED){
                    viewModel.openSuccesDeleteFlow.collect{
                        Toast.makeText(requireContext(), "message", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_cardFragment_to_succesFull)
                    }
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openErrorFlow.collect{code->
                    when(code){
                        ErrorCodes.CARD_NAME->binding.CardNameError.error="Card name error"
                    }

                }
            }}
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openSuccesUpdateFlow.collect{
                    Toast.makeText(requireContext(), "Ishladi", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_cardFragment_to_succesFull)
                }
            }}
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openNetworkFlow.collect {
                    Toast.makeText(requireContext(), "Internet Mavjud emas", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
}