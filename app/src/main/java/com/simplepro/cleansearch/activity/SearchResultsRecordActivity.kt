package com.simplepro.cleansearch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.simplepro.cleansearch.Instance.SearchResultInstance
import com.simplepro.cleansearch.Instance.SearchResultsRecordInstance
import com.simplepro.cleansearch.R
import com.simplepro.cleansearch.adapter.SearchResultsRecordRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search_results_record.*

class SearchResultsRecordActivity : AppCompatActivity() {

    val searchResultsRecordList : ArrayList<SearchResultsRecordInstance> = arrayListOf()
    lateinit var searchResultsRecordRecyclerViewAdapter : SearchResultsRecordRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results_record)

        LeftImageViewSearchResultsRecord.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        searchResultsRecordList.add(SearchResultsRecordInstance("how to use python in android for example", arrayListOf(
            SearchResultInstance("how to use python in android for example", "1", "9012"),
            SearchResultInstance("how to use python in android for example", "2", "8400"),
            SearchResultInstance("how to use python in android for example", "3", "8244"),
            SearchResultInstance("how to use python in android for example", "4", "700"),
            SearchResultInstance("how to use python in android for example", "5", "512"),
            SearchResultInstance("how to use python in android for example", "6", "340"),
            SearchResultInstance("how to use python in android for example", "7", "214"),
            SearchResultInstance("how to use python in android for example", "8", "64"),
            SearchResultInstance("how to use python in android for example", "9", "24"),
            SearchResultInstance("how to use python in android for example", "10", "9"),
            SearchResultInstance("how to use python in android for example", "11", "4")
        )))

        searchResultsRecordRecyclerViewAdapter = SearchResultsRecordRecyclerViewAdapter(searchResultsRecordList)
        recyclerViewSearchResultsRecordActivity.apply {
            adapter = searchResultsRecordRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@SearchResultsRecordActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

    }
}