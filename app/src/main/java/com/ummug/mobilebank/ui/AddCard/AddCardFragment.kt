package com.ummug.mobilebank.ui.AddCard

import android.annotation.SuppressLint
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
import com.ummug.mobilebank.database.Database
import com.ummug.mobilebank.databinding.FragmentAddBinding
import com.ummug.mobilebank.domain.entity.AddCardEntity
import com.ummug.mobilebank.ui.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
 class AddCardFragment : Fragment(R.layout.fragment_add) {

    private val biding: FragmentAddBinding by viewBinding()

    private val database by lazy { Database.getDatabase(requireContext()) }

    private val viewModel:AddCardFragmentViewModel by viewModels()
    private lateinit var addCardEntity: AddCardEntity


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        biding.apply {
            submit.setOnClickListener{

                val date=biding.eDate.text?.toString()?.replace("\\s+".toRegex(), "")

                if (date?.length==5){
                    addCardEntity=AddCardEntity(
                        date.substring(0,1).toInt()
                        ,date.substring(1,date.length).toInt()
                        ,biding.eName.text.toString(),
                        biding.eNumber.text.toString().replace("\\s+".toRegex(), ""))
                }else if (date?.length==6){
                    addCardEntity=AddCardEntity(
                        date.substring(0,2).toInt()
                        ,date.substring(2,date.length).toInt()
                        ,biding.eName.text.toString(),
                        biding.eNumber.text.toString().replace("\\s+".toRegex(), ""))
                }else{
                    Toast.makeText(requireContext(), "Nimadir Nato`g`ri kiritildi", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewModel.addCard(addCardEntity.expire_year,addCardEntity.expire_month,addCardEntity.name,addCardEntity.pan)
                database.contactDao().nukeTable()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openSuccsesScreenFlow.collect{it->
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addCardFragment_to_succesFull)
                }

            }
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openErrorFlow.collect{error->
                    when(error){
                        ErrorCodes.CARD_NAME->biding.eName.error="Wrong name"
                        ErrorCodes.PEN_ERROR->biding.eNumber.error="Wrong Card Number"
                        ErrorCodes.MONH->biding.eDate.error="Wrong Month"
                        ErrorCodes.YEAR->biding.eDate.error="Wrong Year"
                    }

                }

            }
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openNetworkFlow.collect{
                    Toast.makeText(requireContext(), "Internetizni yangilang", Toast.LENGTH_SHORT).show()

                }

            }
        }


    }

}