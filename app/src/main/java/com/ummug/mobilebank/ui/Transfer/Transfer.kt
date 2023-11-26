package com.ummug.mobilebank.ui.Transfer

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.database.Database
import com.ummug.mobilebank.databinding.FragmentTransferBinding
import com.ummug.mobilebank.databinding.ItemCardBinding
import com.ummug.mobilebank.domain.adapters.CardAdapter
import com.ummug.mobilebank.domain.entity.cards.Data
import com.ummug.mobilebank.ui.Card.CardFragment
import com.ummug.mobilebank.ui.Home.HomeFragment
import com.ummug.mobilebank.ui.MyWork
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class Transfer  : Fragment(R.layout.fragment_transfer) {

    private val viewModel:TransferViewModel by viewModels()
    private val binding:FragmentTransferBinding by viewBinding()

    private lateinit var adapter: CardAdapter
    private val database by lazy { Database.getDatabase(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(), database.contactDao().getCards().size.toString(), Toast.LENGTH_SHORT).show()


        adapter= CardAdapter()

        Log.d("fff", database.contactDao().getCards().toString())


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
            val amount=binding.transferAmount.text.toString().replace("\\s+".toRegex(), "")
            if (id!=-1){
                if (!amount.isEmpty() && index.toDouble()<amount.toDouble()){
                    Toast.makeText(requireContext(), "Mablag' yetarli emas", Toast.LENGTH_SHORT).show()
                }
                else{
                    viewModel.transferMoney(id,amount,binding.transferCradNumber.text.toString().replace("\\s+".toRegex(), ""))
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
                        ErrorCodes.MONEY -> binding.transferAmountError.error = "Eng kam summa 5000 so'm"
                        ErrorCodes.PEN_ERROR -> binding.transferCradNumber.error = "Karta raqami notog'ri kiritildi"
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
                viewModel.openTransferFlow.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    onetime(it)
                    android.app.AlertDialog.Builder(requireContext())
                        .setMessage(it)
                        .setTitle("Verification code")
                        .setPositiveButton("ok") { dialog,i ->
                            dialog.dismiss()
                        }.show()
                    findNavController().navigate(R.id.action_transfer2_to_transferVeryfication)
                }
            }
        }
    }


    fun onetime(code:String){
        val constraints=androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(true)
            .build()

        val data = workDataOf("code" to code)
        val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()


        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)

    }
}