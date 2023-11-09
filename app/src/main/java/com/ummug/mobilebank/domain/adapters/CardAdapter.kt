package com.ummug.mobilebank.domain.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ummug.mobilebank.R
import com.ummug.mobilebank.domain.entity.cards.Data

class CardAdapter : ListAdapter<Data, CardViewHolder>(CharacterComparator){
    private var onClickListener: ((Int) -> Unit)? = null
    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun setOnClickClickListener(clickListener: (Int) -> Unit) {
        onClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view, onItemClickListener!!)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it) }
    }

    object CharacterComparator : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}

class CardViewHolder(val view: View, val onItemClickListener: OnItemClickListener ) :
    RecyclerView.ViewHolder(view) {
    init {
        val findViewById = view.findViewById<CardView>(R.id.laout)
        findViewById.setOnClickListener { onItemClickListener.onItemClick(bindingAdapterPosition) }
    }
    private val balance: TextView = view.findViewById(R.id.card_balance)
    private val cardname:TextView=view.findViewById(R.id.name)
    private val card_number:TextView=view.findViewById(R.id.card_number)
    private val date:TextView=view.findViewById(R.id.montandyear)

    fun bind(card: Data) {
        val d=card.amount
        var index=-1
        var counter=0
        for (i in d){
            if (i=='.'){
               index=counter
            }else counter++
        }
        balance.setText("$ " + card.amount.substring(0,index+3))
        cardname.setText(card.name)
        card_number.setText(card.pan.substring(0,4)+" **** **** "+card.pan.substring(12,card.pan.length))
        date.setText(card.expire_month.toString()+"/"+card.expire_year.toString())
    }
}
interface OnItemClickListener {
    fun onItemClick(position: Int)
}