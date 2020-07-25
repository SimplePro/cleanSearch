package com.simplepro.cleansearch.Instance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.simplepro.cleansearch.Converters.Converters

@Entity(tableName = "SearchResultRecords")
data class SearchResultsRecordInstance(
    @ColumnInfo(name = "searchSentences") val searchSentences: String = "",
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "searchResultList") val searchResultList : ArrayList<SearchResultInstance> = arrayListOf(),
    @ColumnInfo(name = "expandable") var expandable : Boolean = false,
    @PrimaryKey(autoGenerate = true) val id : Long = 0
)