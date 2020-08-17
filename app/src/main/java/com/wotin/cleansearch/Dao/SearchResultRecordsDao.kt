package com.wotin.cleansearch.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wotin.cleansearch.CustomClass.SearchResultsRecordCustomClass

@Dao
interface SearchResultRecordsDao {
    @Query("SELECT * from SearchResultRecords")
    fun getAll(): List<SearchResultsRecordCustomClass>

    @Insert
    fun insert(searchResultsRecord: SearchResultsRecordCustomClass)

    @Delete
    fun delete(searchResultsRecord: SearchResultsRecordCustomClass)
}