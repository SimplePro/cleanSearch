package com.wotin.cleansearch.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wotin.cleansearch.R

class AddAndReplaceSynonymRecyclerViewAdapter(val list : ArrayList<String>, val longClickListener : LongClickListener) : RecyclerView.Adapter<AddAndReplaceSynonymRecyclerViewAdapter.CustomViewHolder>() {

    interface LongClickListener {
        fun longClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_and_replace_synonym_recycler_view, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnLongClickListener {
                longClickListener.longClick(position = adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.synonymText.text = list[position]
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val synonymText: TextView = itemView.findViewById(R.id.synonymNounsTextView)
    }
}
