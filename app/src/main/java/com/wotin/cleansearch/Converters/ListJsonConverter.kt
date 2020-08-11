package com.wotin.cleansearch.Converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.wotin.cleansearch.CustomClass.SearchResultCustomClass

class ListJsonConverter {

    @TypeConverter
    fun listToJson(value: ArrayList<SearchResultCustomClass>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): ArrayList<SearchResultCustomClass> {

        val list : ArrayList<SearchResultCustomClass> = ArrayList(Gson().fromJson(value, Array<SearchResultCustomClass>::class.java).toList())
        return list
    }
}