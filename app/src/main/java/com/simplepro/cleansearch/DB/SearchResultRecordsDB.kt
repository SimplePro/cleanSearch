package com.simplepro.cleansearch.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.simplepro.cleansearch.Converters.Converters
import com.simplepro.cleansearch.Dao.SearchResultRecordsDao
import com.simplepro.cleansearch.Instance.SearchResultsRecordInstance

@Database(entities = [SearchResultsRecordInstance::class], version = 1)
@TypeConverters(Converters::class)
abstract class SearchResultRecordsDB : RoomDatabase(){
    @TypeConverters(Converters::class)
    abstract fun searchResultRecordsDB() : SearchResultRecordsDao
}