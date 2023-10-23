package com.ummug.mobilebank.domain.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ummug.mobilebank.R
import com.ummug.mobilebank.domain.entity.CardEntity

class CardsAdapter (private var data:List<CardEntity>): RecyclerView.Adapter<CardsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardsViewHolder(inflate)
    }

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:List<CardEntity>){
        data=list
        notifyDataSetChanged()
    }
}

class CardsViewHolder(view: View) :RecyclerView.ViewHolder(view) {

}
