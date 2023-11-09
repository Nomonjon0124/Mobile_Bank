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

class IstoriyaAdapter : ListAdapter<Data, IstoriyaViewHolder>(CharacterComparator){
    private var onClickListener: ((Int) -> Unit)? = null
    private var onItemClickListener: OnItemIstoriyaClickListener? = null
    fun setOnItemClickListener(listener: OnItemIstoriyaClickListener) {
        onItemClickListener = listener
    }

    fun setOnClickClickListener(clickListener: (Int) -> Unit) {
        onClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IstoriyaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return IstoriyaViewHolder(view, onItemClickListener!!)
    }

    override fun onBindViewHolder(holder: IstoriyaViewHolder, position: Int) {
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

class IstoriyaViewHolder(val view: View, val onItemClickListener: OnItemIstoriyaClickListener ) :
    RecyclerView.ViewHolder(view) {
    init {
        val findViewById = view.findViewById<CardView>(R.id.laout)
        findViewById.setOnClickListener { onItemClickListener.onItemClick(bindingAdapterPosition) }
    }
    private val name: TextView = view.findViewById(R.id.balance)
    private val cardname:TextView=view.findViewById(R.id.adaptercardname)

    fun bind(card: Data) {
        name.setText("$ " + card.amount)
        cardname.setText(card.name)
    }
}
interface OnItemIstoriyaClickListener {
    fun onItemClick(position: Int)
}