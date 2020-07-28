package com.simplepro.cleansearch.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.simplepro.cleansearch.Instance.SearchResultsRecordInstance

@Dao
interface SearchResultRecordsDao {
    @Query("SELECT * from SearchResultRecords")
    fun getAll() : List<SearchResultsRecordInstance>

    @Insert
    fun insert(searchResultsRecord : SearchResultsRecordInstance)

    @Delete
    fun delete(searchResultsRecord : SearchResultsRecordInstance)
}