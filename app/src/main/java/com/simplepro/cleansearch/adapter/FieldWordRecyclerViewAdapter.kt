package com.simplepro.cleansearch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simplepro.cleansearch.R

class FieldWordRecyclerViewAdapter(val FieldList : ArrayList<String>) : RecyclerView.Adapter<FieldWordRecyclerViewAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FieldWordRecyclerViewAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_field_recycler_view, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount")
        return FieldList.size
    }

    override fun onBindViewHolder(holder: FieldWordRecyclerViewAdapter.CustomViewHolder, position: Int) {
        holder.fieldWordText.text = FieldList[position]
        Log.d("TAG", "FiledList[$position] is ${FieldList[position]}")
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val fieldWordText = itemView.findViewById<TextView>(R.id.fieldWordTextView)
    }
}