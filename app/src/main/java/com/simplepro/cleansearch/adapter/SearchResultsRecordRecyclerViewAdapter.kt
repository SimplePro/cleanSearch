package com.simplepro.cleansearch.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simplepro.cleansearch.Instance.SearchResultInstance
import com.simplepro.cleansearch.Instance.SearchResultsRecordInstance
import com.simplepro.cleansearch.R

class SearchResultsRecordRecyclerViewAdapter(val searchResultsRecordList : ArrayList<SearchResultsRecordInstance>)
    : RecyclerView.Adapter<SearchResultsRecordRecyclerViewAdapter.CustomViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_record_list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchResultsRecordList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val searchResultsRecord = searchResultsRecordList[position]
        holder.searchSentence.text = searchResultsRecord.searchSentences

        val recyclerViewAdapter = SearchResultRecyclerViewAdapter(searchResultsRecord.searchResultList as ArrayList<SearchResultInstance>)
        holder.recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }


        val isExpandable : Boolean = searchResultsRecordList[position].expandable
        holder.expandableLayout.visibility = if(isExpandable) View.VISIBLE else View.GONE
        holder.imageView.apply {
            if(isExpandable) setImageResource(R.drawable.top_arrow)
            else setImageResource(R.drawable.bottom_arrow)
        }

        holder.linearLayout.setOnClickListener {
            val searchResultRecord = searchResultsRecordList[position]
            searchResultRecord.expandable = !searchResultRecord.expandable
            notifyItemChanged(position)
        }
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)  {
        val searchSentence = itemView.findViewById<TextView>(R.id.searchSentenceTextViewSearchResultRecord)
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.linearLayoutSearchResultRecord)
        val expandableLayout = itemView.findViewById<RelativeLayout>(R.id.expandableLayoutSearchResultRecord)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewSearchResultsRecord)
        val imageView = itemView.findViewById<ImageView>(R.id.expandableArrowSearchResultRecord)
    }
}
