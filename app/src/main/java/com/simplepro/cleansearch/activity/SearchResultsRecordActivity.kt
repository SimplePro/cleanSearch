package com.simplepro.cleansearch.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.simplepro.cleansearch.DB.SearchResultRecordsDB
import com.simplepro.cleansearch.CustomClass.SearchResultsRecordCustomClass
import com.simplepro.cleansearch.R
import com.simplepro.cleansearch.adapter.SearchResultsRecordRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_search_results_record.*


class SearchResultsRecordActivity : AppCompatActivity() {

    var searchResultsRecordList : ArrayList<SearchResultsRecordCustomClass> = arrayListOf()
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
//            SearchResultInstance("세계 여러나라의 인사법", 1, 124520),
//            SearchResultInstance("나라마다 다른 세계의 인사법", 2, 99746),
//            SearchResultInstance("다른 나라의 인사법은 무엇일까?", 3, 94030),
//            SearchResultInstance("세계 여러나라는 어떤 식으로 인사를 할까?", 4, 80430)
//        )))

        searchResultsRecordList = searchResultRecordsDB.searchResultRecordsDB().getAll() as ArrayList<SearchResultsRecordCustomClass>
//        for (i in 0 .. searchResultsRecordList.size - 1)
//        {
//            searchResultRecordsDB.searchResultRecordsDB().delete(searchResultsRecordList[i])
//        }
//        searchResultsRecordList = searchResultRecordsDB.searchResultRecordsDB().getAll() as ArrayList<SearchResultsRecordInstance>
        if(searchResultsRecordList.isEmpty())
        {
            val animation = AnimationUtils.loadAnimation(this, R.anim.lottie_animation_alpha_visible_animation)
            Handler().postDelayed({
                SearchLottieAnimationView.startAnimation(animation)
            }, 300)
        }
        else {
            SearchLottieAnimationView.visibility = View.GONE
            bottomSearchLottieAnimationTextView.visibility = View.GONE
        }

        searchResultsRecordRecyclerViewAdapter = SearchResultsRecordRecyclerViewAdapter(searchResultsRecordList)
        recyclerViewSearchResultsRecordActivity.apply {
            adapter = searchResultsRecordRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@SearchResultsRecordActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }



    }
}