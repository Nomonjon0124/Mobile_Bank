package com.ummug.mobilebank.domain.adapters

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ummug.mobilebank.databinding.ItemHistoryBinding
import com.ummug.mobilebank.domain.entity.History.HistoryRsponse

class IstoryAdapter (
    val list:List<HistoryRsponse>,
    val onItemClick:(HistoryRsponse,Int)->Unit
     ) : RecyclerView.Adapter<IstoryAdapter.VH>() {

inner class VH(var itemHistoryBinding: ItemHistoryBinding):RecyclerView.ViewHolder(itemHistoryBinding.root){
    fun onBind(historyRsponse: HistoryRsponse,position:Int){
           itemHistoryBinding.apply {
               itemDate.text=historyRsponse.data[position].card.expire_month.toString()+historyRsponse.data[position].card.expire_year.toString()
               status.text=historyRsponse.data[position].is_output.toString()
               company.text=historyRsponse.data[position].card.owner
               itemMoney.text=historyRsponse.data[position].card.amount

               itemView.setOnClickListener { onItemClick.invoke(historyRsponse,position)}

           }

    }
}

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
        return VH(ItemHistoryBinding.inflate(LayoutInflater.from(p0.context),p0,false))
    }

    override fun getItemCount(): Int=list.size

    override fun onBindViewHolder(p0: VH, p1: Int) {
        p0.onBind(list[p1],p1)
    }

}
