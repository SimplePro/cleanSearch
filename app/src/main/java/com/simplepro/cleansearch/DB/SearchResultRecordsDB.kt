package com.simplepro.cleansearch.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.simplepro.cleansearch.Converters.ListJsonConverter
import com.simplepro.cleansearch.Dao.SearchResultRecordsDao
import com.simplepro.cleansearch.CustomClass.SearchResultsRecordCustomClass

@Database(entities = [SearchResultsRecordCustomClass::class], version = 1)
@TypeConverters(ListJsonConverter::class)
abstract class SearchResultRecordsDB : RoomDatabase(){
    @TypeConverters(ListJsonConverter::class)
    abstract fun searchResultRecordsDB() : SearchResultRecordsDao
}