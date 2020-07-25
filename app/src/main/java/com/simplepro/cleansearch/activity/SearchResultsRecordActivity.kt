package com.simplepro.cleansearch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.simplepro.cleansearch.DB.SearchResultRecordsDB
import com.simplepro.cleansearch.Instance.SearchResultInstance
import com.simplepro.cleansearch.Instance.SearchResultsRecordInstance
import com.simplepro.cleansearch.R
import com.simplepro.cleansearch.adapter.SearchResultsRecordRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search_results_record.*

class SearchResultsRecordActivity : AppCompatActivity() {

    var searchResultsRecordList : ArrayList<SearchResultsRecordInstance> = arrayListOf()
    lateinit var searchResultsRecordRecyclerViewAdapter : SearchResultsRecordRecyclerViewAdapter
    lateinit var searchResultRecordsDB : SearchResultRecordsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results_record)

        LeftImageViewSearchResultsRecord.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        searchResultRecordsDB = Room.databaseBuilder(
            applicationContext,
            SearchResultRecordsDB::class.java, "searchResultRecords.db"
        ).allowMainThreadQueries()
            .build()

//        searchResultRecordsDB.searchResultRecordsDB().insert(SearchResultsRecordInstance("세계 사람들의 인사법", arrayListOf(
//            SearchResultInstance("세계 여러나라의 인사법", "1", "124520"),
//            SearchResultInstance("나라마다 다른 세계의 인사법", "2", "99746"),
//            SearchResultInstance("다른 나라의 인사법은 무엇일까?", "3", "94030"),
//            SearchResultInstance("세계 여러나라는 어떤 식으로 인사를 할까?", "4", "80430")
//        )))

        searchResultsRecordList = searchResultRecordsDB.searchResultRecordsDB().getAll() as ArrayList<SearchResultsRecordInstance>

        searchResultsRecordRecyclerViewAdapter = SearchResultsRecordRecyclerViewAdapter(searchResultsRecordList)
        recyclerViewSearchResultsRecordActivity.apply {
            adapter = searchResultsRecordRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@SearchResultsRecordActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

    }
}