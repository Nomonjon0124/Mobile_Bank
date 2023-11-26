package com.ummug.mobilebank.ui.Payment

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.database.Database
import com.ummug.mobilebank.databinding.FragmentPaymentBinding
import com.ummug.mobilebank.domain.adapters.CardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Payment :Fragment(R.layout.fragment_payment){
    private lateinit var adapter: CardAdapter
    private val database by lazy { Database.getDatabase(requireContext()) }

    private val binding:FragmentPaymentBinding by viewBinding()

    private val viewModel:PaymentViewModel by viewModels ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Toast.makeText(requireContext(), database.contactDao().getCards().size.toString(), Toast.LENGTH_SHORT).show()

        adapter= CardAdapter()
        binding.transferRV.adapter=adapter

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.transferRV)

        var id=-1
        var index="";
        adapter.setOnItemClickListener(object : com.ummug.mobilebank.domain.adapters.OnItemClickListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int) {
                val item=database.contactDao().getCards().get(position)
                AlertDialog.Builder(requireContext())
                    .setTitle("Karta ma'lumotlari")
                    .setMessage("${item.id} , ${item.expire_month}/${item.expire_year} , \n ${item.pan} , ${item.name}")
                    .setPositiveButton("OK") { dialog, _ ->
                        id=item.id
                        index=item.amount
                        dialog.dismiss()
                    }.show()
            }
        })
        binding.send.setOnClickListener {
            val amount=binding.paymentamount.text.toString().replace("\\s+".toRegex(), "")
            if (id!=-1){
                if (!amount.isEmpty() && index.toDouble()<amount.toDouble()){
                    Toast.makeText(requireContext(), "Mablag' yetarli emas", Toast.LENGTH_SHORT).show()
                }
                else{
                    viewModel.paymentAmount(id,amount,binding.phoneSignIn.text.toString().replace("\\s+".toRegex(), ""))
                    database.contactDao().nukeTable()
                }
            }
            else{
                Toast.makeText(requireContext(), "from_card id yo'q", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.submitList(database.contactDao().getCards())


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.errorFlow.collect {
                    when (it) {
                        ErrorCodes.MONEY -> binding.transferAmountError.error = "Eng kam summa 1000 so'm"
                        ErrorCodes.PHONE_NUMBER -> binding.phoneNumber.error = "Telefon raqami hato"
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.noNetworkFlow.collect {
                    Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openPaymentFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    android.app.AlertDialog.Builder(requireContext())
                        .setMessage(it)
                        .setTitle("Verification code")
                        .setPositiveButton("ok") { dialog,i ->
                            dialog.dismiss()
                        }.show()
                    findNavController().navigate(R.id.action_payment2_to_transferVeryfication)
                }
            }
        }
    }
}