package com.ummug.mobilebank.domain.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ummug.mobilebank.databinding.ItemCardBinding
import com.ummug.mobilebank.databinding.ItemHistoryBinding
import com.ummug.mobilebank.databinding.ItemTransacationsBinding
import com.ummug.mobilebank.domain.entity.cardistory.Data
import com.ummug.mobilebank.domain.entity.cardistory.cardHistory

class ForHistoryCardAdapter(
    var list:List<Data>,
    val onItemClick:(Data,Int)->Unit
): RecyclerView.Adapter<ForHistoryCardAdapter.VH>()  {

    inner class VH(var itemTransacationsBinding: ItemTransacationsBinding):RecyclerView.ViewHolder(itemTransacationsBinding.root){

        @SuppressLint("SetTextI18n")
        fun onBind(data:Data, position:Int){
            itemTransacationsBinding.apply {

                    status.text="Succesfully ${data.is_output.toString()}"
                transactionamount.text=data.amount.toString()
                cardId.text="Card id -> ${data.id}"
                itemView.setOnClickListener { onItemClick.invoke(data,position)}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTransacationsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position],position)
    }
}