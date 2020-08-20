package com.wotin.cleansearch.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wotin.cleansearch.Converters.ListJsonConverterSearchResultCustomClass
import com.wotin.cleansearch.Dao.SearchResultRecordsDao
import com.wotin.cleansearch.CustomClass.SearchResultsRecordCustomClass

@Database(entities = [SearchResultsRecordCustomClass::class], version = 1)
@TypeConverters(ListJsonConverterSearchResultCustomClass::class)
abstract class SearchResultRecordsDB : RoomDatabase() {
    @TypeConverters(ListJsonConverterSearchResultCustomClass::class)
    abstract fun searchResultRecordsDB(): SearchResultRecordsDao
}