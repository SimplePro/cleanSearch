package com.wotin.cleansearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wotin.cleansearch.CustomClass.SearchResultCustomClass
import com.wotin.cleansearch.CustomClass.SearchResultsRecordCustomClass
import com.wotin.cleansearch.R
import java.util.*
import kotlin.collections.ArrayList

class SearchResultsRecordRecyclerViewAdapter(val searchResultsRecordList: ArrayList<SearchResultsRecordCustomClass>) :
    RecyclerView.Adapter<SearchResultsRecordRecyclerViewAdapter.CustomViewHolder>(), Filterable {

    lateinit var context: Context
    lateinit var searchResultRecordListSearch : ArrayList<SearchResultsRecordCustomClass>

    init {
        searchResultRecordListSearch = searchResultsRecordList
        notifyItemChanged(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_record_list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchResultRecordListSearch.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val searchResultsRecord = searchResultRecordListSearch[position]
        holder.searchSentence.text = searchResultsRecord.searchSentences

        val recyclerViewAdapter =
            SearchResultRecyclerViewAdapter(searchResultsRecord.searchResultList as ArrayList<SearchResultCustomClass>)
        holder.recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }


        val isExpandable: Boolean = searchResultRecordListSearch[position].expandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.imageView.apply {
            if (isExpandable) setImageResource(R.drawable.top_arrow)
            else setImageResource(R.drawable.bottom_arrow)
        }

        holder.linearLayout.setOnClickListener {
            val searchResultRecord = searchResultRecordListSearch[position]
            searchResultRecord.expandable = !searchResultRecord.expandable
            notifyItemChanged(position)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchSentence =
            itemView.findViewById<TextView>(R.id.searchSentenceTextViewSearchResultRecord)
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.linearLayoutSearchResultRecord)
        val expandableLayout =
            itemView.findViewById<RelativeLayout>(R.id.expandableLayoutSearchResultRecord)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewSearchResultsRecord)
        val imageView = itemView.findViewById<ImageView>(R.id.expandableArrowSearchResultRecord)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if(charSearch.isEmpty())
                {
                    searchResultRecordListSearch = searchResultsRecordList
                } else {
                    val resultList = ArrayList<SearchResultsRecordCustomClass>()
                    for (row in searchResultsRecordList)
                    {
                        if(row.searchSentences.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))){
                            resultList.add(row)
                        }
                    }
                    searchResultRecordListSearch = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = searchResultRecordListSearch
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchResultRecordListSearch = results?.values as ArrayList<SearchResultsRecordCustomClass>
                notifyDataSetChanged()
            }

        }
    }
}
