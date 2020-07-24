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

        searchResultsRecordList.add(SearchResultsRecordInstance("how to use python in android", arrayListOf(
            SearchResultInstance("how to use python in android for example", "1", "9012"),
            SearchResultInstance("how to use python in android example", "2", "8400"),
            SearchResultInstance("how to use python in android", "3", "8244"),
            SearchResultInstance("relation between python and android", "4", "700"),
            SearchResultInstance("how to use android in python for example", "5", "512"),
            SearchResultInstance("how to use android in python example", "6", "340"),
            SearchResultInstance("how to use android in python", "7", "214"),
            SearchResultInstance("how to use python android", "8", "64"),
            SearchResultInstance("how to use android python", "9", "24"),
            SearchResultInstance("how to make python file in android", "10", "9"),
            SearchResultInstance("how to make python file", "11", "4")
        )))

        searchResultsRecordList.add(SearchResultsRecordInstance("세계 사람들의 인사법", arrayListOf(
            SearchResultInstance("세계 여러나라의 인사법", "1", "124520"),
            SearchResultInstance("나라마다 다른 세계의 인사법", "2", "99746"),
            SearchResultInstance("다른 나라의 인사법은 무엇일까?", "3", "94030"),
            SearchResultInstance("세계 여러나라는 어떤 식으로 인사를 할까?", "4", "80430")
        )))

        searchResultsRecordRecyclerViewAdapter = SearchResultsRecordRecyclerViewAdapter(searchResultsRecordList)
        recyclerViewSearchResultsRecordActivity.apply {
            adapter = searchResultsRecordRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@SearchResultsRecordActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

    }
}