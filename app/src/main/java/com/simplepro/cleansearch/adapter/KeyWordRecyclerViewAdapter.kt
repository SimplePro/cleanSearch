package com.simplepro.cleansearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simplepro.cleansearch.R

class KeyWordRecyclerViewAdapter(val keyWordList : ArrayList<String>, private val itemViewSetOnLongClick : ItemViewSetOnLongClickListener) : RecyclerView.Adapter<KeyWordRecyclerViewAdapter.CustomViewHolder>() {

    interface ItemViewSetOnLongClickListener {
        fun itemViewLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyWordRecyclerViewAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_key_word_recycler_view, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnLongClickListener {
                itemViewSetOnLongClick.itemViewLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int {
        return keyWordList.size
    }

    override fun onBindViewHolder(
        holder: KeyWordRecyclerViewAdapter.CustomViewHolder,
        position: Int
    ) {
        holder.keyWordText.text = keyWordList[position]
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val keyWordText = itemView.findViewById<TextView>(R.id.keyWordTextView)
    }
}