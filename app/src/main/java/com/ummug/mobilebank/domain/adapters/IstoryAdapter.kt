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
    var list:List<com.ummug.mobilebank.domain.entity.History.Data>,
    val onItemClick:(com.ummug.mobilebank.domain.entity.History.Data,Int)->Unit
     ) : RecyclerView.Adapter<IstoryAdapter.VH>() {

inner class VH(var itemHistoryBinding: ItemHistoryBinding):RecyclerView.ViewHolder(itemHistoryBinding.root){

   open fun setList(data: List<com.ummug.mobilebank.domain.entity.History.Data>){
        list=data
        notifyDataSetChanged()
    }
    fun onBind(historyRsponse: com.ummug.mobilebank.domain.entity.History.Data,position:Int){
           itemHistoryBinding.apply {
               itemDate.text=historyRsponse.card.expire_month.toString()+historyRsponse.card.expire_year.toString()
               status.text=historyRsponse.is_output.toString()
               company.text=historyRsponse.card.owner

               var counter=0;
               for (i in historyRsponse.card.amount){
                   if (i== '.'){
                       break
                   }else counter++;
               }
               itemMoney.text=historyRsponse.card.amount.substring(0,counter+2)

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
