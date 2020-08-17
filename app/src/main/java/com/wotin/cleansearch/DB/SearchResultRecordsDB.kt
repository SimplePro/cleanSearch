package com.wotin.cleansearch.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wotin.cleansearch.Converters.ListJsonConverter
import com.wotin.cleansearch.Dao.SearchResultRecordsDao
import com.wotin.cleansearch.CustomClass.SearchResultsRecordCustomClass

@Database(entities = [SearchResultsRecordCustomClass::class], version = 1)
@TypeConverters(ListJsonConverter::class)
abstract class SearchResultRecordsDB : RoomDatabase() {
    @TypeConverters(ListJsonConverter::class)
    abstract fun searchResultRecordsDB(): SearchResultRecordsDao
}